package com.vku.eventmanagement.common.exception;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
    Instant timestamp,
    int status,
    String error,
    String message,
    String code,
    String path,
    Object details,
    List<FieldViolation> violations) {
  public record FieldViolation(String field, String message) {}
}
