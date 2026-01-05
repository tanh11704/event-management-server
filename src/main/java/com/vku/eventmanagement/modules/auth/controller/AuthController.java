package com.vku.eventmanagement.modules.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vku.eventmanagement.common.response.ApiResponse;
import com.vku.eventmanagement.modules.auth.dto.request.LoginRequest;
import com.vku.eventmanagement.modules.auth.dto.request.RefreshTokenRequest;
import com.vku.eventmanagement.modules.auth.dto.request.RegisterRequest;
import com.vku.eventmanagement.modules.auth.dto.response.AuthResponse;
import com.vku.eventmanagement.modules.auth.dto.response.UserResponse;
import com.vku.eventmanagement.modules.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ApiResponse<UserResponse> register(@Valid @RequestBody final RegisterRequest request) {
    return ApiResponse.success(authService.register(request));
  }

  @PostMapping("/login")
  public ApiResponse<AuthResponse> login(@Valid @RequestBody final LoginRequest request) {
    return ApiResponse.success(authService.login(request));
  }

  @PostMapping("/refresh")
  public ApiResponse<AuthResponse> refreshToken(
      @Valid @RequestBody final RefreshTokenRequest request) {
    return ApiResponse.success(authService.refreshToken(request));
  }

  @PostMapping("/logout")
  public ApiResponse<Void> logout(@Valid @RequestBody final RefreshTokenRequest request) {
    authService.logout(request.getRefreshToken());
    return ApiResponse.success();
  }

  @PostMapping("/logout-all")
  public ApiResponse<Void> logoutAll() {
    authService.logoutAll();
    return ApiResponse.success();
  }
}
