package com.vku.eventmanagement.modules.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

  private String accessToken;
  private String refreshToken;
  private long expiresIn;
  private String tokenType;
  private UserResponse user;

  public static AuthResponse of(
      final String accessToken,
      final String refreshToken,
      final long expiresIn,
      final UserResponse user) {
    return AuthResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .expiresIn(expiresIn)
        .tokenType("Bearer")
        .user(user)
        .build();
  }
}
