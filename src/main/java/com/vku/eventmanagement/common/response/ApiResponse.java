package com.vku.eventmanagement.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vku.eventmanagement.common.exception.ErrorResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ApiResponse<T> {

  private final boolean success;
  private final T data;
  private final ErrorResponse error;

  private ApiResponse(final boolean success, final T data, final ErrorResponse error) {
    this.success = success;
    this.data = data;
    this.error = error;
  }

  public static <T> ApiResponse<T> success(final T data) {
    return new ApiResponse<>(true, data, null);
  }

  public static ApiResponse<Void> success() {
    return new ApiResponse<>(true, null, null);
  }

  public static <T> ApiResponse<T> error(final ErrorResponse error) {
    return new ApiResponse<>(false, null, error);
  }

  public boolean isSuccess() {
    return success;
  }

  public T getData() {
    return data;
  }

  public ErrorResponse getError() {
    return error;
  }
}
