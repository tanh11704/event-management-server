package com.vku.eventmanagement.modules.auth.config;

import com.vku.eventmanagement.modules.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled task to cleanup expired password reset tokens. Keeps tokens for audit purposes
 * (default: 7 days).
 */
@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(
    value = "app.password-reset-token.cleanup.enabled",
    havingValue = "true",
    matchIfMissing = true)
public class PasswordResetTokenCleanupScheduler {

  private static final int DEFAULT_RETENTION_DAYS = 7;

  private final AuthService authService;

  /**
   * Cleanup expired password reset tokens daily at 3 AM (Vietnam time, UTC+7). Deletes tokens
   * expired more than 7 days ago.
   */
  @Scheduled(cron = "0 0 3 * * ?", zone = "Asia/Ho_Chi_Minh") // Daily at 3 AM Vietnam time
  public void cleanupExpiredTokens() {
    try {
      final int deletedCount =
          authService.cleanupExpiredPasswordResetTokens(DEFAULT_RETENTION_DAYS);
      if (deletedCount > 0) {
        log.info("Cleaned up {} expired password reset tokens", deletedCount);
      }
    } catch (final Exception e) {
      log.error("Error cleaning up expired password reset tokens", e);
    }
  }
}
