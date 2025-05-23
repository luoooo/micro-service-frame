package com.example.starter.config;

import com.example.database.config.DynamicDataSourceConfig;
import com.example.database.config.MybatisPlusConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnProperty(prefix = "micro.service", name = "database.enabled", havingValue = "true")
@Import({
    DynamicDataSourceConfig.class,
    MybatisPlusConfig.class
})
public class DatabaseAutoConfiguration {
} 