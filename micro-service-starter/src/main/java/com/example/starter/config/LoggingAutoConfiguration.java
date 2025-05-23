package com.example.starter.config;

import com.example.logging.config.LoggingConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnProperty(prefix = "micro.service", name = "logging.enabled", havingValue = "true")
@Import({
    LoggingConfig.class
})
public class LoggingAutoConfiguration {
} 