package com.example.logging.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = {"classpath:logback-spring.xml"})
public class LoggingConfig {
    // Logging configuration is handled by logback-spring.xml
    // This class is just for importing the XML configuration
} 