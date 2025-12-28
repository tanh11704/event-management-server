package com.vku.eventmanagement.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {

  private String issuer;
  private String audience;
  private TokenConfig accessToken = new TokenConfig();
  private TokenConfig refreshToken = new TokenConfig();

  @Getter
  @Setter
  public static class TokenConfig {
    private String secret;
    private long expiration;
  }
}
