package com.vku.eventmanagement.modules.auth.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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
import com.vku.eventmanagement.modules.auth.dto.request.ChangePasswordRequest;
import com.vku.eventmanagement.modules.auth.dto.request.ForgotPasswordRequest;
import com.vku.eventmanagement.modules.auth.dto.request.LoginRequest;
import com.vku.eventmanagement.modules.auth.dto.request.RefreshTokenRequest;
import com.vku.eventmanagement.modules.auth.dto.request.RegisterRequest;
import com.vku.eventmanagement.modules.auth.dto.request.ResetPasswordRequest;
import com.vku.eventmanagement.modules.auth.dto.response.AuthResponse;
import com.vku.eventmanagement.modules.auth.dto.response.UserResponse;
import com.vku.eventmanagement.modules.auth.entity.PasswordResetTokenEntity;
import com.vku.eventmanagement.modules.auth.entity.RefreshTokenEntity;
import com.vku.eventmanagement.modules.auth.entity.SystemRole;
import com.vku.eventmanagement.modules.auth.entity.UserEntity;
import com.vku.eventmanagement.modules.auth.entity.UserStatus;
import com.vku.eventmanagement.modules.auth.mapper.UserMapper;
import com.vku.eventmanagement.modules.auth.repository.PasswordResetTokenRepository;
import com.vku.eventmanagement.modules.auth.repository.RefreshTokenRepository;
import com.vku.eventmanagement.modules.auth.repository.UserRepository;
import com.vku.eventmanagement.modules.notification.service.EmailService;
import com.vku.eventmanagement.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

  private static final long MILLIS_PER_SECOND = 1000L;

  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final PasswordResetTokenRepository passwordResetTokenRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final EmailService emailService;

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

  @Override
  public UserResponse getCurrentUser() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new ApiException(
          HttpStatus.UNAUTHORIZED, ErrorCode.AUTH_UNAUTHORIZED, "Người dùng chưa đăng nhập");
    }

    final UUID userId = UUID.fromString(authentication.getName());
    final UserEntity user =
        userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new ApiException(
                        HttpStatus.NOT_FOUND,
                        ErrorCode.USER_NOT_FOUND,
                        "Người dùng không tồn tại"));

    return userMapper.toResponse(user);
  }

  @Override
  public void changePassword(final ChangePasswordRequest request) {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new ApiException(
          HttpStatus.UNAUTHORIZED, ErrorCode.AUTH_UNAUTHORIZED, "Người dùng chưa đăng nhập");
    }

    final UUID userId = UUID.fromString(authentication.getName());
    final UserEntity user =
        userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new ApiException(
                        HttpStatus.NOT_FOUND,
                        ErrorCode.USER_NOT_FOUND,
                        "Người dùng không tồn tại"));

    final String passwordHash = user.getPasswordHash();
    if (passwordHash == null || passwordHash.trim().isEmpty()) {
      throw new ApiException(
          HttpStatus.INTERNAL_SERVER_ERROR,
          ErrorCode.COMMON_INTERNAL_ERROR,
          "Lỗi hệ thống: Mật khẩu không hợp lệ");
    }

    if (!passwordHash.startsWith("$2a$")
        && !passwordHash.startsWith("$2b$")
        && !passwordHash.startsWith("$2y$")) {
      throw new ApiException(
          HttpStatus.INTERNAL_SERVER_ERROR,
          ErrorCode.COMMON_INTERNAL_ERROR,
          "Lỗi hệ thống: Định dạng mật khẩu không hợp lệ. Vui lòng đặt lại mật khẩu.");
    }

    if (!passwordEncoder.matches(request.getOldPassword(), passwordHash)) {
      throw new ApiException(
          HttpStatus.BAD_REQUEST,
          ErrorCode.AUTH_INVALID_CREDENTIALS,
          "Mật khẩu cũ không chính xác");
    }

    user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user);

    refreshTokenRepository.deleteByUserId(user.getId());
  }

  @Override
  public void forgotPassword(final ForgotPasswordRequest request) {
    final String resetToken = generateResetToken();
    final String tokenHash = hashToken(resetToken);

    userRepository
        .findByEmail(request.getEmail())
        .filter(user -> user.getStatus() == UserStatus.ACTIVE)
        .ifPresent(
            user -> {
              final PasswordResetTokenEntity resetTokenEntity =
                  PasswordResetTokenEntity.builder()
                      .userId(user.getId())
                      .tokenHash(tokenHash)
                      .expiresAt(Instant.now().plusSeconds(3600))
                      .build();
              passwordResetTokenRepository.save(resetTokenEntity);

              log.info("Password reset requested for email={}", user.getEmail());

              // Send email asynchronously to prevent timing attacks
              // This ensures response time is consistent regardless of email sending duration
              emailService.sendPasswordResetEmailAsync(user.getEmail(), resetToken);
            });

    // ALWAYS return OK (no exception thrown)
    // Token is generated and hashed even if email doesn't exist (discarded, not saved)
    // Email is sent asynchronously, so response time is consistent
  }

  @Override
  public void resetPassword(final ResetPasswordRequest request) {
    final String tokenHash = hashToken(request.getToken());

    final PasswordResetTokenEntity resetToken =
        passwordResetTokenRepository
            .findByTokenHash(tokenHash)
            .orElseThrow(
                () ->
                    new ApiException(
                        HttpStatus.BAD_REQUEST,
                        ErrorCode.AUTH_INVALID_TOKEN,
                        "Token không hợp lệ"));

    if (!resetToken.isValid()) {
      throw new ApiException(
          HttpStatus.BAD_REQUEST,
          ErrorCode.AUTH_TOKEN_EXPIRED,
          "Token đã hết hạn hoặc đã được sử dụng");
    }

    final UserEntity user =
        userRepository
            .findById(resetToken.getUserId())
            .orElseThrow(
                () ->
                    new ApiException(
                        HttpStatus.NOT_FOUND,
                        ErrorCode.USER_NOT_FOUND,
                        "Người dùng không tồn tại"));

    // Check user status
    if (user.getStatus() != UserStatus.ACTIVE) {
      throw new ApiException(
          HttpStatus.FORBIDDEN, ErrorCode.AUTH_ACCOUNT_SUSPENDED, "Tài khoản không còn hoạt động");
    }

    // Update password
    user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user);

    // Mark token as used with audit timestamp
    resetToken.setUsedAt(Instant.now());
    passwordResetTokenRepository.save(resetToken);

    // Revoke all refresh tokens for security
    refreshTokenRepository.deleteByUserId(user.getId());
  }

  @Override
  public int cleanupExpiredPasswordResetTokens(final int retentionDays) {
    final Instant cutoffDate = Instant.now().minusSeconds(retentionDays * 24L * 3600L);
    return passwordResetTokenRepository.deleteExpiredTokensOlderThan(cutoffDate);
  }

  private String generateResetToken() {
    // Generate cryptographically secure random token (256-bit entropy)
    final byte[] randomBytes = new byte[32];
    new SecureRandom().nextBytes(randomBytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
  }

  private String hashToken(final String token) {
    try {
      final MessageDigest digest = MessageDigest.getInstance("SHA-256");
      final byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
      // Use URL-safe Base64 encoding (no padding, no + / = characters)
      return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
    } catch (final NoSuchAlgorithmException e) {
      throw new ApiException(
          HttpStatus.INTERNAL_SERVER_ERROR,
          ErrorCode.COMMON_INTERNAL_ERROR,
          "Thuật toán SHA-256 không khả dụng");
    }
  }
}
