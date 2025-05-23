package com.example.rpc.config;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Request.Options feignOptions() {
        return new Request.Options(5000, 5000);
    }

    @Bean
    public Retryer feignRetryer() {
        // Retry 5 times, first retry after 100ms, max retry after 1s
        return new Retryer.Default(100, 1000, 5);
    }
} 