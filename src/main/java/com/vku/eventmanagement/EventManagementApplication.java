package com.vku.eventmanagement;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class EventManagementApplication {

  public static void main(final String[] args) {
    SpringApplication.run(EventManagementApplication.class, args);
  }

  @Bean(initMethod = "migrate")
  @ConditionalOnProperty(
      value = "spring.flyway.enabled",
      havingValue = "true",
      matchIfMissing = true)
  public Flyway flyway(final DataSource dataSource) {

    final Flyway flyway =
        Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:db/migration")
            .baselineOnMigrate(true)
            .load();

    return flyway;
  }
}
