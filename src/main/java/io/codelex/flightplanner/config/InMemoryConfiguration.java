package io.codelex.flightplanner.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "flight-app", name = "storage", havingValue = "in-memory")
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class InMemoryConfiguration {
}
