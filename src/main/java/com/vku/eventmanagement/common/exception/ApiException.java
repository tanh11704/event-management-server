package com.vku.eventmanagement.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public final class ApiException extends RuntimeException {
  private static final HttpStatusCode DEFAULT_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

  private final HttpStatusCode status;
  private final String code;
  private final Object details;

  public ApiException(final HttpStatusCode status, final String message) {
    this(status, null, message, null, null);
  }

  public ApiException(final HttpStatusCode status, final ErrorCode code, final String message) {
    this(status, code == null ? null : code.name(), message, null, null);
  }

  public ApiException(final HttpStatusCode status, final String code, final String message) {
    this(status, code, message, null, null);
  }

  public ApiException(
      final HttpStatusCode status,
      final ErrorCode code,
      final String message,
      final Object details) {
    this(status, code == null ? null : code.name(), message, details, null);
  }

  public ApiException(
      final HttpStatusCode status, final String code, final String message, final Object details) {
    this(status, code, message, details, null);
  }

  public ApiException(
      final HttpStatusCode status,
      final String code,
      final String message,
      final Object details,
      final Throwable cause) {
    super(message, cause);
    this.status = status == null ? DEFAULT_STATUS : status;
    this.code = code;
    this.details = details;
  }

  public HttpStatusCode getStatus() {
    return status;
  }

  public String getCode() {
    return code;
  }

  public Object getDetails() {
    return details;
  }
}
