package com.msrazavi.test.pooyabyte.task.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

/**
 * @author Mehdi Sadat Razavi
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.msrazavi.test.pooyabyte.common.repository")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EntityScan(basePackages = {"com.msrazavi.test.pooyabyte.common.schema.entity"})
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of(Thread.currentThread().getName());
    }
}
