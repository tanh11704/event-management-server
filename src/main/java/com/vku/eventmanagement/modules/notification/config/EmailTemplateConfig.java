package com.vku.eventmanagement.modules.notification.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class EmailTemplateConfig {

  @Value("${app.email.template.cache:true}")
  private boolean cacheEnabled;

  @Bean
  public SpringResourceTemplateResolver emailTemplateResolver() {
    final SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setPrefix("classpath:/mail/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setCacheable(cacheEnabled);
    templateResolver.setCheckExistence(true);
    templateResolver.setOrder(1);
    return templateResolver;
  }

  @Bean(name = "emailTemplateEngine")
  public SpringTemplateEngine emailTemplateEngine(
      @Qualifier("emailTemplateResolver")
          final SpringResourceTemplateResolver emailTemplateResolver) {
    final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(emailTemplateResolver);
    return templateEngine;
  }
}
