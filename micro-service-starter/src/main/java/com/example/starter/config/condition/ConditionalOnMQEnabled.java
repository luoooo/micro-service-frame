package com.example.starter.config.condition;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnMQEnabledCondition.class)
public @interface ConditionalOnMQEnabled {
}

class OnMQEnabledCondition extends OnPropertyEnabledCondition {
    public OnMQEnabledCondition() {
        super("micro.service.mq.enabled", true);
    }
} 