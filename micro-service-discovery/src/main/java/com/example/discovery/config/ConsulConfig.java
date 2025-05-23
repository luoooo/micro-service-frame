package com.example.discovery.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
public class ConsulConfig {
    // Consul configuration is handled by Spring Cloud Consul
    // This class is just for enabling service discovery
} 