package com.vku.eventmanagement.modules.auth.service;

import com.vku.eventmanagement.modules.auth.dto.request.LoginRequest;
import com.vku.eventmanagement.modules.auth.dto.request.RefreshTokenRequest;
import com.vku.eventmanagement.modules.auth.dto.request.RegisterRequest;
import com.vku.eventmanagement.modules.auth.dto.response.AuthResponse;
import com.vku.eventmanagement.modules.auth.dto.response.UserResponse;

public interface AuthService {

  UserResponse register(RegisterRequest request);

  AuthResponse login(LoginRequest request);

  AuthResponse refreshToken(RefreshTokenRequest request);

  void logout(String refreshToken);

  void logoutAll();
}
