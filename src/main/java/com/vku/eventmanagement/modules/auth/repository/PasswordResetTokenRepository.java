package com.vku.eventmanagement.modules.auth.repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vku.eventmanagement.modules.auth.entity.PasswordResetTokenEntity;

@Repository
public interface PasswordResetTokenRepository
    extends JpaRepository<PasswordResetTokenEntity, UUID> {

  Optional<PasswordResetTokenEntity> findByTokenHash(String tokenHash);

  void deleteByUserId(UUID userId);

  void deleteByExpiresAtBefore(Instant expiresAt);

  @Modifying
  @Query("DELETE FROM PasswordResetTokenEntity t WHERE t.expiresAt < :cutoffDate")
  int deleteExpiredTokensOlderThan(@Param("cutoffDate") Instant cutoffDate);
}
