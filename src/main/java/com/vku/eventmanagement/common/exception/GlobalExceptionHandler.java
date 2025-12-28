package com.vku.eventmanagement.common.exception;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public final class GlobalExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  private record ErrorExtras(Object details, List<ErrorResponse.FieldViolation> violations) {}

  private static final String EMPTY = "";
  private static final String PREFIX_MISSING_REQUIRED_PARAMETER = "Thiếu tham số bắt buộc: ";

  private static final String MSG_VALIDATION_FAILED = "Dữ liệu không hợp lệ";
  private static final String MSG_MALFORMED_JSON = "Định dạng JSON không hợp lệ";
  private static final String MSG_UNAUTHORIZED = "Chưa xác thực";
  private static final String MSG_FORBIDDEN = "Không có quyền truy cập";
  private static final String MSG_DATA_INTEGRITY_VIOLATION = "Vi phạm ràng buộc dữ liệu";
  private static final String MSG_INTERNAL_SERVER_ERROR = "Lỗi hệ thống";

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ErrorResponse> handleApiException(
      final ApiException ex, final HttpServletRequest request) {
    LOG.warn("ApiException: {} - {}", ex.getCode(), ex.getMessage());
    final HttpStatusCode status = ex.getStatus();
    return ResponseEntity.status(status)
        .body(
            buildFromApiException(
                status,
                ex.getMessage(),
                ex.getCode(),
                request,
                new ErrorExtras(ex.getDetails(), null)));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex, final HttpServletRequest request) {
    final HttpStatus status = HttpStatus.BAD_REQUEST;
    final List<ErrorResponse.FieldViolation> violations =
        ex.getBindingResult().getFieldErrors().stream()
            .map(err -> new ErrorResponse.FieldViolation(err.getField(), err.getDefaultMessage()))
            .toList();
    LOG.debug("Validation error: {} violations", violations.size());
    return ResponseEntity.status(status)
        .body(
            build(
                status,
                MSG_VALIDATION_FAILED,
                ErrorCode.COMMON_VALIDATION_ERROR,
                request,
                new ErrorExtras(null, violations)));
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<ErrorResponse> handleBindException(
      final BindException ex, final HttpServletRequest request) {
    final HttpStatus status = HttpStatus.BAD_REQUEST;
    final List<ErrorResponse.FieldViolation> violations =
        ex.getBindingResult().getFieldErrors().stream()
            .map(err -> new ErrorResponse.FieldViolation(err.getField(), err.getDefaultMessage()))
            .toList();
    LOG.debug("Bind error: {} violations", violations.size());
    return ResponseEntity.status(status)
        .body(
            build(
                status,
                MSG_VALIDATION_FAILED,
                ErrorCode.COMMON_VALIDATION_ERROR,
                request,
                new ErrorExtras(null, violations)));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolation(
      final ConstraintViolationException ex, final HttpServletRequest request) {
    final HttpStatus status = HttpStatus.BAD_REQUEST;
    final List<ErrorResponse.FieldViolation> violations =
        ex.getConstraintViolations().stream()
            .map(
                v ->
                    new ErrorResponse.FieldViolation(
                        v.getPropertyPath().toString(), v.getMessage()))
            .toList();
    LOG.debug("Constraint violation: {} violations", violations.size());
    return ResponseEntity.status(status)
        .body(
            build(
                status,
                MSG_VALIDATION_FAILED,
                ErrorCode.COMMON_VALIDATION_ERROR,
                request,
                new ErrorExtras(null, violations)));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleNotReadable(
      final HttpMessageNotReadableException ex, final HttpServletRequest request) {
    final HttpStatus status = HttpStatus.BAD_REQUEST;
    LOG.debug("Malformed JSON: {}", ex.getMessage());
    return ResponseEntity.status(status)
        .body(
            build(
                status,
                MSG_MALFORMED_JSON,
                ErrorCode.COMMON_MALFORMED_JSON,
                request,
                new ErrorExtras(null, null)));
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingParam(
      final MissingServletRequestParameterException ex, final HttpServletRequest request) {
    final HttpStatus status = HttpStatus.BAD_REQUEST;
    final String msg = PREFIX_MISSING_REQUIRED_PARAMETER + ex.getParameterName();
    LOG.debug("Missing parameter: {}", ex.getParameterName());
    return ResponseEntity.status(status)
        .body(
            build(
                status,
                msg,
                ErrorCode.COMMON_MISSING_PARAMETER,
                request,
                new ErrorExtras(null, null)));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrity(
      final DataIntegrityViolationException ex, final HttpServletRequest request) {
    final HttpStatus status = HttpStatus.CONFLICT;
    // TODO: Có thể refine thành USER_EMAIL_ALREADY_EXISTS, EVENT_DUPLICATE_REGISTRATION
    LOG.warn("Data integrity violation: {}", ex.getMostSpecificCause().getMessage());
    return ResponseEntity.status(status)
        .body(
            build(
                status,
                MSG_DATA_INTEGRITY_VIOLATION,
                ErrorCode.COMMON_DATA_INTEGRITY_VIOLATION,
                request,
                new ErrorExtras(null, null)));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleAuthentication(
      final AuthenticationException ex, final HttpServletRequest request) {
    final HttpStatus status = HttpStatus.UNAUTHORIZED;
    LOG.debug("Authentication failed: {}", ex.getMessage());
    return ResponseEntity.status(status)
        .body(
            build(
                status,
                MSG_UNAUTHORIZED,
                ErrorCode.AUTH_UNAUTHORIZED,
                request,
                new ErrorExtras(null, null)));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDenied(
      final AccessDeniedException ex, final HttpServletRequest request) {
    final HttpStatus status = HttpStatus.FORBIDDEN;
    LOG.debug("Access denied: {}", ex.getMessage());
    return ResponseEntity.status(status)
        .body(
            build(
                status,
                MSG_FORBIDDEN,
                ErrorCode.AUTH_FORBIDDEN,
                request,
                new ErrorExtras(null, null)));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneric(
      final Exception ex, final HttpServletRequest request) {
    final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    LOG.error("Unhandled exception at {}: ", request.getRequestURI(), ex);
    return ResponseEntity.status(status)
        .body(
            build(
                status,
                MSG_INTERNAL_SERVER_ERROR,
                ErrorCode.COMMON_INTERNAL_ERROR,
                request,
                new ErrorExtras(null, null)));
  }

  private ErrorResponse build(
      final HttpStatusCode status,
      final String message,
      final ErrorCode code,
      final HttpServletRequest request,
      final ErrorExtras extras) {
    return buildFromApiException(status, message, code.name(), request, extras);
  }

  private ErrorResponse buildFromApiException(
      final HttpStatusCode status,
      final String message,
      final String code,
      final HttpServletRequest request,
      final ErrorExtras extras) {
    final String reason = status instanceof HttpStatus hs ? hs.getReasonPhrase() : EMPTY;
    final String path = request != null ? request.getRequestURI() : null;
    return new ErrorResponse(
        Instant.now(),
        status.value(),
        reason,
        message,
        code,
        path,
        extras == null ? null : extras.details,
        extras == null ? null : extras.violations);
  }
}
