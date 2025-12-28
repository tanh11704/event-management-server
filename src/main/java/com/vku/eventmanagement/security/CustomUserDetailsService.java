package com.vku.eventmanagement.security;

import java.util.UUID;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vku.eventmanagement.modules.auth.entity.UserEntity;
import com.vku.eventmanagement.modules.auth.entity.UserStatus;
import com.vku.eventmanagement.modules.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  /**
   * Load user by email - used for login authentication flow.
   *
   * @param email the user's email
   * @return UserDetails for the user
   * @throws UsernameNotFoundException if user not found
   */
  @Override
  public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
    final UserEntity user =
        userRepository
            .findByEmail(email)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException("Không tìm thấy người dùng với email: " + email));
    return buildUserDetails(user);
  }

  /**
   * Load user by ID - used for JWT token authentication flow.
   *
   * @param userId the user's UUID as string
   * @return UserDetails for the user
   * @throws UsernameNotFoundException if user not found
   */
  public UserDetails loadUserById(final String userId) throws UsernameNotFoundException {
    try {
      final UUID id = UUID.fromString(userId);
      final UserEntity user =
          userRepository
              .findById(id)
              .orElseThrow(
                  () ->
                      new UsernameNotFoundException("Không tìm thấy người dùng với ID: " + userId));
      return buildUserDetails(user);
    } catch (final IllegalArgumentException e) {
      throw new UsernameNotFoundException("ID người dùng không hợp lệ: " + userId);
    }
  }

  private UserDetails buildUserDetails(final UserEntity user) {
    // Map SystemRole to Spring Security authority
    final String authority = "ROLE_" + user.getSystemRole().name();

    return User.builder()
        .username(user.getId().toString())
        .password(user.getPasswordHash())
        .disabled(user.getStatus() != UserStatus.ACTIVE)
        .accountExpired(false)
        .credentialsExpired(false)
        .accountLocked(user.getStatus() == UserStatus.SUSPENDED)
        .authorities(authority)
        .build();
  }
}
