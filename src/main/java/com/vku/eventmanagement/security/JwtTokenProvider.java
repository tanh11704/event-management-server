package com.vku.eventmanagement.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.vku.eventmanagement.common.exception.ApiException;
import com.vku.eventmanagement.common.exception.ErrorCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

  private static final String TOKEN_TYPE_CLAIM = "type";
  private static final String TOKEN_TYPE_ACCESS = "ACCESS";
  private static final String TOKEN_TYPE_REFRESH = "REFRESH";

  private final SecretKey accessSecretKey;
  private final SecretKey refreshSecretKey;
  private final JwtProperties jwtProperties;

  public JwtTokenProvider(final JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
    this.accessSecretKey =
        Keys.hmacShaKeyFor(
            jwtProperties.getAccessToken().getSecret().getBytes(StandardCharsets.UTF_8));
    this.refreshSecretKey =
        Keys.hmacShaKeyFor(
            jwtProperties.getRefreshToken().getSecret().getBytes(StandardCharsets.UTF_8));
  }

  public String generateAccessToken(final UUID userId, final String email) {
    final Date now = new Date();
    final Date expiry = new Date(now.getTime() + jwtProperties.getAccessToken().getExpiration());

    return Jwts.builder()
        .subject(userId.toString())
        .claim("email", email)
        .claim(TOKEN_TYPE_CLAIM, TOKEN_TYPE_ACCESS)
        .issuer(jwtProperties.getIssuer())
        .audience()
        .add(jwtProperties.getAudience())
        .and()
        .issuedAt(now)
        .expiration(expiry)
        .signWith(accessSecretKey)
        .compact();
  }

  public String generateRefreshToken(final UUID userId) {
    final Date now = new Date();
    final Date expiry = new Date(now.getTime() + jwtProperties.getRefreshToken().getExpiration());

    return Jwts.builder()
        .subject(userId.toString())
        .id(UUID.randomUUID().toString())
        .claim(TOKEN_TYPE_CLAIM, TOKEN_TYPE_REFRESH)
        .issuer(jwtProperties.getIssuer())
        .audience()
        .add(jwtProperties.getAudience())
        .and()
        .issuedAt(now)
        .expiration(expiry)
        .signWith(refreshSecretKey)
        .compact();
  }

  public UUID getUserIdFromAccessToken(final String token) {
    final Claims claims = parseAccessToken(token);
    validateTokenType(claims, TOKEN_TYPE_ACCESS);
    return UUID.fromString(claims.getSubject());
  }

  public UUID getUserIdFromRefreshToken(final String token) {
    final Claims claims = parseRefreshToken(token);
    validateTokenType(claims, TOKEN_TYPE_REFRESH);
    return UUID.fromString(claims.getSubject());
  }

  public boolean validateAccessToken(final String token) {
    try {
      final Claims claims = parseAccessToken(token);
      validateTokenType(claims, TOKEN_TYPE_ACCESS);
      return true;
    } catch (final JwtException | ApiException e) {
      return false;
    }
  }

  public boolean validateRefreshToken(final String token) {
    try {
      final Claims claims = parseRefreshToken(token);
      validateTokenType(claims, TOKEN_TYPE_REFRESH);
      return true;
    } catch (final JwtException | ApiException e) {
      return false;
    }
  }

  public long getAccessTokenExpiration() {
    return jwtProperties.getAccessToken().getExpiration();
  }

  public long getRefreshTokenExpiration() {
    return jwtProperties.getRefreshToken().getExpiration();
  }

  private Claims parseAccessToken(final String token) {
    try {
      return Jwts.parser()
          .verifyWith(accessSecretKey)
          .requireIssuer(jwtProperties.getIssuer())
          .requireAudience(jwtProperties.getAudience())
          .build()
          .parseSignedClaims(token)
          .getPayload();
    } catch (final ExpiredJwtException e) {
      throw new ApiException(
          HttpStatus.UNAUTHORIZED, ErrorCode.TOKEN_EXPIRED, "Access token đã hết hạn");
    } catch (final JwtException e) {
      throw new ApiException(
          HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_TOKEN, "Access token không hợp lệ");
    }
  }

  private Claims parseRefreshToken(final String token) {
    try {
      return Jwts.parser()
          .verifyWith(refreshSecretKey)
          .requireIssuer(jwtProperties.getIssuer())
          .requireAudience(jwtProperties.getAudience())
          .build()
          .parseSignedClaims(token)
          .getPayload();
    } catch (final ExpiredJwtException e) {
      throw new ApiException(
          HttpStatus.UNAUTHORIZED, ErrorCode.TOKEN_EXPIRED, "Refresh token đã hết hạn");
    } catch (final JwtException e) {
      throw new ApiException(
          HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_TOKEN, "Refresh token không hợp lệ");
    }
  }

  private void validateTokenType(final Claims claims, final String expectedType) {
    final String tokenType = claims.get(TOKEN_TYPE_CLAIM, String.class);
    if (!expectedType.equals(tokenType)) {
      throw new ApiException(
          HttpStatus.UNAUTHORIZED,
          ErrorCode.INVALID_TOKEN,
          "Token type không hợp lệ. Yêu cầu: " + expectedType);
    }
  }
}
