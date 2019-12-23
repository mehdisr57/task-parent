package com.msrazavi.test.pooyabyte.request.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

/**
 * @author Mehdi Sadat Razavi
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EntityScan(basePackages = {"com.msrazavi.test.pooyabyte.common.schema.entity"})
@EnableJpaRepositories(basePackages = "com.msrazavi.test.pooyabyte.common.repository")
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of(Thread.currentThread().getName());
    }
}
