package com.vku.eventmanagement.modules.auth.entity;

/** System Role - Vai trò hệ thống cố định, gắn với tài khoản người dùng. */
public enum SystemRole {

  /** Quản trị viên hệ thống - có toàn quyền. */
  SYSTEM_ADMIN,

  /** Cán bộ phòng Công tác Sinh viên. */
  CTSV_STAFF,

  /** Cán bộ phòng Đào tạo. */
  TRAINING_STAFF,

  /** Người dùng thông thường (sinh viên, giảng viên...). */
  USER,

  /** Ban lãnh đạo - chỉ xem báo cáo. */
  LEADER
}
