package com.example.mq.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class BaseMQConsumer<T> implements RocketMQListener<String> {

    @Autowired
    protected ObjectMapper objectMapper;

    @Override
    public void onMessage(String message) {
        try {
            T data = objectMapper.readValue(message, getMessageType());
            log.info("Received message: {}", message);
            handleMessage(data);
        } catch (Exception e) {
            log.error("Failed to process message: {}", message, e);
            handleError(message, e);
        }
    }

    /**
     * Get message type class
     *
     * @return message type class
     */
    protected abstract Class<T> getMessageType();

    /**
     * Handle message
     *
     * @param message message object
     */
    protected abstract void handleMessage(T message);

    /**
     * Handle error
     *
     * @param message original message string
     * @param e       exception
     */
    protected void handleError(String message, Exception e) {
        // Default implementation does nothing
        // Override this method to implement custom error handling
    }
} 