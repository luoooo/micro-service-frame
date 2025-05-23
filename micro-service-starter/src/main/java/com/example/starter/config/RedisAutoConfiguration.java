package com.example.starter.config;

import com.example.redis.config.RedisConfig;
import com.example.redis.util.RedisUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnProperty(prefix = "micro.service", name = "redis.enabled", havingValue = "true")
@Import({
    RedisConfig.class,
    RedisUtil.class
})
public class RedisAutoConfiguration {
} 