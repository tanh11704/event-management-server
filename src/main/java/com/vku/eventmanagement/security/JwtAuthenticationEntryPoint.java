package com.vku.eventmanagement.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vku.eventmanagement.common.exception.ErrorCode;
import com.vku.eventmanagement.common.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;
  private final RequestMappingHandlerMapping handlerMapping;

  @Override
  public void commence(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final AuthenticationException authException)
      throws IOException {

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");

    // Check if endpoint exists before returning 401
    try {
      final Object handler = handlerMapping.getHandler(request);
      if (handler == null) {
        // Endpoint doesn't exist, return 404
        response.setStatus(HttpStatus.NOT_FOUND.value());
        final ErrorResponse errorResponse =
            new ErrorResponse(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                "Endpoint không tồn tại",
                ErrorCode.COMMON_NOT_FOUND.name(),
                request.getRequestURI(),
                null,
                null);
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
        return;
      }
    } catch (final Exception e) {
      // If we can't determine, assume endpoint exists and return 401
    }

    // Endpoint exists but not authenticated, return 401
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    final ErrorResponse errorResponse =
        new ErrorResponse(
            Instant.now(),
            HttpStatus.UNAUTHORIZED.value(),
            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
            "Chưa xác thực. Vui lòng đăng nhập.",
            ErrorCode.AUTH_UNAUTHORIZED.name(),
            request.getRequestURI(),
            null,
            null);

    objectMapper.writeValue(response.getOutputStream(), errorResponse);
  }
}
