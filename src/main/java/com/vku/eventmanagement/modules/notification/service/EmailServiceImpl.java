package com.vku.eventmanagement.modules.notification.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;

  public EmailServiceImpl(
      final JavaMailSender mailSender,
      @Qualifier("emailTemplateEngine") final TemplateEngine templateEngine) {
    this.mailSender = mailSender;
    this.templateEngine = templateEngine;
  }

  @Value("${app.password-reset.base-url:http://localhost:8080}")
  private String baseUrl;

  @Value("${spring.mail.username}")
  private String fromEmail;

  @Value("${app.email.sender-name:Event Management System}")
  private String senderName;

  @Override
  public void sendPasswordResetEmail(final String toEmail, final String resetToken) {
    try {
      final MimeMessage message = mailSender.createMimeMessage();
      final MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

      helper.setFrom(new InternetAddress(fromEmail, senderName));
      helper.setTo(toEmail);
      helper.setSubject("Đặt lại mật khẩu - Event Management System");

      final String resetUrl = baseUrl + "/reset-password?token=" + resetToken;

      // Load template from resources/mail/auth/reset-password.html
      final Context context = new Context();
      context.setVariable("resetUrl", resetUrl);
      context.setVariable("resetToken", resetToken);

      final String htmlContent = templateEngine.process("auth/reset-password", context);

      helper.setText(htmlContent, true);
      mailSender.send(message);

      log.info("Password reset email sent successfully to email={}", toEmail);
    } catch (final MessagingException | UnsupportedEncodingException e) {
      log.error("Failed to send password reset email to email={}", toEmail, e);
      throw new RuntimeException("Failed to send password reset email", e);
    }
  }

  @Override
  @Async
  public void sendPasswordResetEmailAsync(final String toEmail, final String resetToken) {
    try {
      sendPasswordResetEmail(toEmail, resetToken);
    } catch (final Exception e) {
      log.error(
          "Failed to send password reset email asynchronously to email={}. Token was still"
              + " generated.",
          toEmail,
          e);
    }
  }
}
