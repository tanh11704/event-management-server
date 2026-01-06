package com.vku.eventmanagement.modules.notification.service;

public interface EmailService {

  void sendPasswordResetEmail(String toEmail, String resetToken);

  void sendPasswordResetEmailAsync(String toEmail, String resetToken);
}
