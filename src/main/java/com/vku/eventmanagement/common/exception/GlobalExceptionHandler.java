package com.vku.eventmanagement.common.exception;

import java.time.Instant;
import java.util.List;

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

  private record ErrorExtras(Object details, List<ErrorResponse.FieldViolation> violations) {
  }

  private static final String EMPTY = "";
  private static final String PREFIX_MISSING_REQUIRED_PARAMETER = "Missing required parameter: ";

  private static final String MSG_VALIDATION_FAILED = "Validation failed";
  private static final String MSG_MALFORMED_JSON = "Malformed JSON request";
  private static final String MSG_UNAUTHORIZED = "Unauthorized";
  private static final String MSG_FORBIDDEN = "Forbidden";
  private static final String MSG_DATA_INTEGRITY_VIOLATION = "Data integrity violation";
  private static final String MSG_INTERNAL_SERVER_ERROR = "Internal server error";

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ErrorResponse> handleApiException(
      final ApiException ex, final HttpServletRequest request) {
    final HttpStatusCode status = ex.getStatus();
    return ResponseEntity.status(status)
        .body(
            build(
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
    final List<ErrorResponse.FieldViolation> violations = ex.getBindingResult().getFieldErrors().stream()
        .map(err -> new ErrorResponse.FieldViolation(err.getField(), err.getDefaultMessage()))
        .toList();
    return ResponseEntity.status(status)
        .body(
            build(
                status,
                MSG_VALIDATION_FAILED,
                ErrorCode.VALIDATION_ERROR.name(),
                request,
                new ErrorExtras(null, violations)));
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<ErrorResponse> handleBindException(
      final BindException ex, final HttpServletRequest request) {
    final HttpStatus status = HttpStatus.BAD_REQUEST;
    final List<ErrorResponse.FieldViolation> violations = ex.getBindingResult().getFieldErrors().stream()
        .map(err -> new ErrorResponse.FieldViolation(err.getField(), err.getDefaultMessage()))
        .toList();
    return ResponseEntity.status(status)
        .body(
            build(
                status,
                MSG_VALIDATION_FAILED,
                ErrorCode.VALIDATION_ERROR.name(),
                request,
                new ErrorExtras(null, violations)));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolation(
      final ConstraintViolationException ex, final HttpServletRequest request) {
    final HttpStatus status = HttpStatus.BAD_REQUEST;
    final List<ErrorResponse.FieldViolation> violations = ex.getConstraintViolations().stream()
        .map(
            v -> new ErrorResponse.FieldViolation(
                v.getPropertyPath().toString(), v.getMessage()))
        .toList();
    return ResponseEntity.status(status)
        .body(
            build(
                status,
                MSG_VALIDATION_FAILED,
                ErrorCode.VALIDATION_ERROR.name(),
                request,
                new ErrorExtras(null, violations)));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleNotReadable(
      final HttpMessageNotReadableException ex, final HttpServletRequest request) {
    final HttpStatus status = HttpStatus.BAD_REQUEST;
    return ResponseEntity.status(status)
        .body(
            build(
                status,
                MSG_MALFORMED_JSON,
                ErrorCode.MALFORMED_JSON.name(),
                request,
                new ErrorExtras(null, null)));
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingParam(
      final MissingServletRequestParameterException ex, final HttpServletRequest request) {
    final HttpStatus status = HttpStatus.BAD_REQUEST;
    final String msg = PREFIX_MISSING_REQUIRED_PARAMETER + ex.getParameterName();
    return ResponseEntity.status(status)
        .body(
            build(
                status,
                msg,
                ErrorCode.MISSING_PARAMETER.name(),
                request,
                new ErrorExtras(null, null)));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrity(
      final DataIntegrityViolationException ex, final HttpServletRequest request) {
    final HttpStatus status = HttpStatus.CONFLICT;
    return ResponseEntity.status(status)
        .body(
            build(
                status,
                MSG_DATA_INTEGRITY_VIOLATION,
                ErrorCode.DATA_INTEGRITY_VIOLATION.name(),
                request,
                new ErrorExtras(null, null)));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleAuthentication(
      final AuthenticationException ex, final HttpServletRequest request) {
    final HttpStatus status = HttpStatus.UNAUTHORIZED;
    return ResponseEntity.status(status)
        .body(
            build(
                status,
                MSG_UNAUTHORIZED,
                ErrorCode.UNAUTHORIZED.name(),
                request,
                new ErrorExtras(null, null)));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDenied(
      final AccessDeniedException ex, final HttpServletRequest request) {
    final HttpStatus status = HttpStatus.FORBIDDEN;
    return ResponseEntity.status(status)
        .body(
            build(
                status,
                MSG_FORBIDDEN,
                ErrorCode.FORBIDDEN.name(),
                request,
                new ErrorExtras(null, null)));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneric(
      final Exception ex, final HttpServletRequest request) {
    final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    return ResponseEntity.status(status)
        .body(
            build(
                status,
                MSG_INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_ERROR.name(),
                request,
                new ErrorExtras(null, null)));
  }

  private ErrorResponse build(
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
