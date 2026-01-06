package com.vku.eventmanagement.modules.auth.controller;

import com.vku.eventmanagement.common.response.ApiResponse;
import com.vku.eventmanagement.modules.auth.dto.request.ChangePasswordRequest;
import com.vku.eventmanagement.modules.auth.dto.request.ForgotPasswordRequest;
import com.vku.eventmanagement.modules.auth.dto.request.LoginRequest;
import com.vku.eventmanagement.modules.auth.dto.request.RefreshTokenRequest;
import com.vku.eventmanagement.modules.auth.dto.request.RegisterRequest;
import com.vku.eventmanagement.modules.auth.dto.request.ResetPasswordRequest;
import com.vku.eventmanagement.modules.auth.dto.response.AuthResponse;
import com.vku.eventmanagement.modules.auth.dto.response.UserResponse;
import com.vku.eventmanagement.modules.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @GetMapping("/me")
  public ApiResponse<UserResponse> getCurrentUser() {
    return ApiResponse.success(authService.getCurrentUser());
  }

  @PostMapping("/change-password")
  public ApiResponse<Void> changePassword(@Valid @RequestBody final ChangePasswordRequest request) {
    authService.changePassword(request);
    return ApiResponse.success();
  }

  @PostMapping("/forgot-password")
  public ApiResponse<Void> forgotPassword(@Valid @RequestBody final ForgotPasswordRequest request) {
    authService.forgotPassword(request);
    return ApiResponse.success();
  }

  @PostMapping("/reset-password")
  public ApiResponse<Void> resetPassword(@Valid @RequestBody final ResetPasswordRequest request) {
    authService.resetPassword(request);
    return ApiResponse.success();
  }
}
