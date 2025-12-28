package com.vku.eventmanagement.modules.auth.dto.response;

import java.util.UUID;

import com.vku.eventmanagement.modules.auth.entity.SystemRole;
import com.vku.eventmanagement.modules.auth.entity.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

  private UUID id;
  private String email;
  private UserStatus status;
  private SystemRole systemRole;
}
