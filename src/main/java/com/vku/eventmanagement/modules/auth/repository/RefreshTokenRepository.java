package com.vku.eventmanagement.modules.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vku.eventmanagement.modules.auth.entity.RefreshTokenEntity;
import com.vku.eventmanagement.modules.auth.entity.UserEntity;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

  Optional<RefreshTokenEntity> findByTokenHash(String tokenHash);

  @Modifying
  @Query("DELETE FROM RefreshTokenEntity r WHERE r.user = :user")
  void deleteByUser(@Param("user") UserEntity user);

  @Modifying
  @Query("DELETE FROM RefreshTokenEntity r WHERE r.user.id = :userId")
  void deleteByUserId(@Param("userId") UUID userId);
}
