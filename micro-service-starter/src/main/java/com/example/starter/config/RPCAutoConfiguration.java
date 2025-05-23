package com.example.starter.config;

import com.example.rpc.config.DubboConfig;
import com.example.rpc.config.FeignConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableFeignClients
@ConditionalOnProperty(prefix = "micro.service", name = "rpc.enabled", havingValue = "true")
@Import({
    DubboConfig.class,
    FeignConfig.class
})
public class RPCAutoConfiguration {
} 