package com.example.starter.config;

import com.example.discovery.config.ConsulConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnProperty(prefix = "micro.service", name = "discovery.enabled", havingValue = "true")
@Import({
    ConsulConfig.class
})
public class DiscoveryAutoConfiguration {
} 