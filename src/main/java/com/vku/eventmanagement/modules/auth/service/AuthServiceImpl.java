package com.vku.eventmanagement.modules.auth.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vku.eventmanagement.common.exception.ApiException;
import com.vku.eventmanagement.common.exception.ErrorCode;
import com.vku.eventmanagement.modules.auth.dto.request.LoginRequest;
import com.vku.eventmanagement.modules.auth.dto.request.RefreshTokenRequest;
import com.vku.eventmanagement.modules.auth.dto.request.RegisterRequest;
import com.vku.eventmanagement.modules.auth.dto.response.AuthResponse;
import com.vku.eventmanagement.modules.auth.dto.response.UserResponse;
import com.vku.eventmanagement.modules.auth.entity.RefreshTokenEntity;
import com.vku.eventmanagement.modules.auth.entity.SystemRole;
import com.vku.eventmanagement.modules.auth.entity.UserEntity;
import com.vku.eventmanagement.modules.auth.entity.UserStatus;
import com.vku.eventmanagement.modules.auth.mapper.UserMapper;
import com.vku.eventmanagement.modules.auth.repository.RefreshTokenRepository;
import com.vku.eventmanagement.modules.auth.repository.UserRepository;
import com.vku.eventmanagement.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private static final long MILLIS_PER_SECOND = 1000L;

  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public UserResponse register(final RegisterRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new ApiException(
          HttpStatus.CONFLICT, ErrorCode.USER_EMAIL_ALREADY_EXISTS, "Email đã được sử dụng");
    }

    final UserEntity user = userMapper.toEntity(request);
    user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
    user.setStatus(UserStatus.ACTIVE);
    user.setSystemRole(SystemRole.USER); // Default role for public registration

    final UserEntity savedUser = userRepository.save(user);
    return userMapper.toResponse(savedUser);
  }

  @Override
  public AuthResponse login(final LoginRequest request) {
    final UserEntity user =
        userRepository
            .findByEmail(request.getEmail())
            .orElseThrow(
                () ->
                    new ApiException(
                        HttpStatus.UNAUTHORIZED,
                        ErrorCode.AUTH_INVALID_CREDENTIALS,
                        "Email hoặc mật khẩu không chính xác"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
      throw new ApiException(
          HttpStatus.UNAUTHORIZED,
          ErrorCode.AUTH_INVALID_CREDENTIALS,
          "Email hoặc mật khẩu không chính xác");
    }

    if (user.getStatus() == UserStatus.SUSPENDED) {
      throw new ApiException(
          HttpStatus.FORBIDDEN, ErrorCode.AUTH_ACCOUNT_SUSPENDED, "Tài khoản đã bị khóa");
    }

    if (user.getStatus() == UserStatus.DELETED) {
      throw new ApiException(
          HttpStatus.UNAUTHORIZED,
          ErrorCode.AUTH_INVALID_CREDENTIALS,
          "Email hoặc mật khẩu không chính xác");
    }

    return createAuthResponse(user);
  }

  @Override
  public AuthResponse refreshToken(final RefreshTokenRequest request) {
    // Validate JWT signature and type first
    if (!jwtTokenProvider.validateRefreshToken(request.getRefreshToken())) {
      throw new ApiException(
          HttpStatus.UNAUTHORIZED, ErrorCode.AUTH_INVALID_TOKEN, "Refresh token không hợp lệ");
    }

    final String tokenHash = hashToken(request.getRefreshToken());

    final RefreshTokenEntity refreshToken =
        refreshTokenRepository
            .findByTokenHash(tokenHash)
            .orElseThrow(
                () ->
                    new ApiException(
                        HttpStatus.UNAUTHORIZED,
                        ErrorCode.AUTH_INVALID_TOKEN,
                        "Refresh token không tồn tại trong hệ thống"));

    if (!refreshToken.isValid()) {
      throw new ApiException(
          HttpStatus.UNAUTHORIZED,
          ErrorCode.AUTH_TOKEN_EXPIRED,
          "Refresh token đã hết hạn hoặc đã bị thu hồi");
    }

    // Revoke old refresh token
    refreshToken.setRevokedAt(Instant.now());
    refreshTokenRepository.save(refreshToken);

    // Manually fetch user by userId
    final UserEntity user =
        userRepository
            .findById(refreshToken.getUserId())
            .orElseThrow(
                () ->
                    new ApiException(
                        HttpStatus.NOT_FOUND,
                        ErrorCode.USER_NOT_FOUND,
                        "Người dùng không tồn tại"));

    if (user.getStatus() != UserStatus.ACTIVE) {
      throw new ApiException(
          HttpStatus.FORBIDDEN, ErrorCode.AUTH_ACCOUNT_SUSPENDED, "Tài khoản không còn hoạt động");
    }

    return createAuthResponse(user);
  }

  @Override
  public void logout(final String refreshToken) {
    final String tokenHash = hashToken(refreshToken);

    refreshTokenRepository
        .findByTokenHash(tokenHash)
        .ifPresent(
            token -> {
              token.setRevokedAt(Instant.now());
              refreshTokenRepository.save(token);
            });
  }

  @Override
  public void logoutAll() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      final UUID userId = UUID.fromString(authentication.getName());
      refreshTokenRepository.deleteByUserId(userId);
    }
  }

  private AuthResponse createAuthResponse(final UserEntity user) {
    final String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getEmail());
    final String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

    // Save refresh token hash to database
    final RefreshTokenEntity refreshTokenEntity =
        RefreshTokenEntity.builder()
            .userId(user.getId())
            .tokenHash(hashToken(refreshToken))
            .expiresAt(Instant.now().plusMillis(jwtTokenProvider.getRefreshTokenExpiration()))
            .build();
    refreshTokenRepository.save(refreshTokenEntity);

    return AuthResponse.of(
        accessToken,
        refreshToken,
        jwtTokenProvider.getAccessTokenExpiration() / MILLIS_PER_SECOND,
        userMapper.toResponse(user));
  }

  private String hashToken(final String token) {
    try {
      final MessageDigest digest = MessageDigest.getInstance("SHA-256");
      final byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(hash);
    } catch (final NoSuchAlgorithmException e) {
      throw new ApiException(
          HttpStatus.INTERNAL_SERVER_ERROR,
          ErrorCode.COMMON_INTERNAL_ERROR,
          "Thuật toán SHA-256 không khả dụng");
    }
  }
}
