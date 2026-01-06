package com.vku.eventmanagement.common.exception;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

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
