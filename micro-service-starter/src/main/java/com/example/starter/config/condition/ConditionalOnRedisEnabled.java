package com.example.starter.config.condition;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnRedisEnabledCondition.class)
public @interface ConditionalOnRedisEnabled {
}

class OnRedisEnabledCondition extends OnPropertyEnabledCondition {
    public OnRedisEnabledCondition() {
        super("micro.service.redis.enabled", true);
    }
} 