package com.vku.eventmanagement.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
