package com.example.mq.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MQProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * Send sync message
     *
     * @param topic   message topic
     * @param message message content
     * @param <T>     message type
     */
    public <T> void syncSend(String topic, T message) {
        log.info("Sending sync message to topic: {}, message: {}", topic, message);
        rocketMQTemplate.convertAndSend(topic, message);
    }

    /**
     * Send async message
     *
     * @param topic   message topic
     * @param message message content
     * @param <T>     message type
     */
    public <T> void asyncSend(String topic, T message) {
        log.info("Sending async message to topic: {}, message: {}", topic, message);
        rocketMQTemplate.asyncSend(topic, message, null);
    }

    /**
     * Send delay message
     *
     * @param topic      message topic
     * @param message    message content
     * @param delayLevel delay level (1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h)
     * @param <T>        message type
     */
    public <T> void sendDelayMessage(String topic, T message, int delayLevel) {
        log.info("Sending delay message to topic: {}, message: {}, delayLevel: {}", topic, message, delayLevel);
        Message<T> msg = MessageBuilder.withPayload(message).build();
        rocketMQTemplate.syncSend(topic, msg, 3000, delayLevel);
    }

    /**
     * Send transaction message
     *
     * @param topic   message topic
     * @param message message content
     * @param <T>     message type
     */
    public <T> void sendTransactionMessage(String topic, T message) {
        log.info("Sending transaction message to topic: {}, message: {}", topic, message);
        Message<T> msg = MessageBuilder.withPayload(message).build();
        rocketMQTemplate.sendMessageInTransaction(topic, msg, null);
    }
} 