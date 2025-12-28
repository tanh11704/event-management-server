package com.vku.eventmanagement;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EventManagementApplication {

  public static void main(final String[] args) {
    SpringApplication.run(EventManagementApplication.class, args);
  }

  @Bean(initMethod = "migrate")
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
