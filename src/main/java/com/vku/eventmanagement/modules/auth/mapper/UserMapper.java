package com.vku.eventmanagement.modules.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vku.eventmanagement.modules.auth.dto.request.RegisterRequest;
import com.vku.eventmanagement.modules.auth.dto.response.UserResponse;
import com.vku.eventmanagement.modules.auth.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "passwordHash", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "systemRole", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  UserEntity toEntity(RegisterRequest request);

  UserResponse toResponse(UserEntity entity);
}
