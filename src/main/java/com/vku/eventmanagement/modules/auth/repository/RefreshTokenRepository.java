package com.vku.eventmanagement.modules.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vku.eventmanagement.modules.auth.entity.RefreshTokenEntity;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

  Optional<RefreshTokenEntity> findByTokenHash(String tokenHash);

  @Modifying
  @Query(value = "DELETE FROM refresh_tokens WHERE user_id = :userId", nativeQuery = true)
  void deleteByUserId(@Param("userId") UUID userId);
}
