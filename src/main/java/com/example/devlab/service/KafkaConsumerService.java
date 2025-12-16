package com.example.devlab.service;

import com.example.devlab.dto.EventMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Kafka Consumer 서비스
 * 이벤트 메시지 수신 및 처리
 */
@Slf4j
@Service
public class KafkaConsumerService {

    /**
     * 이벤트 메시지 수신
     */
    @KafkaListener(topics = "events", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeEvent(EventMessage message) {
        log.info("Received event: {}", message);
        log.info("Event ID: {}, Type: {}, Payload: {}, Timestamp: {}",
                message.getEventId(),
                message.getEventType(),
                message.getPayload(),
                message.getTimestamp());

        // 이벤트 처리 로직
        processEvent(message);
    }

    /**
     * 알림 메시지 수신
     */
    @KafkaListener(topics = "notifications", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeNotification(String message) {
        log.info("Received notification: {}", message);

        // 알림 처리 로직
        processNotification(message);
    }

    /**
     * 이벤트 처리
     */
    private void processEvent(EventMessage message) {
        // 실제 비즈니스 로직 구현
        log.info("Processing event: {}", message.getEventType());

        switch (message.getEventType()) {
            case "USER_CREATED":
                log.info("Handling user creation event");
                break;
            case "ORDER_PLACED":
                log.info("Handling order placement event");
                break;
            case "PRODUCT_UPDATED":
                log.info("Handling product update event");
                break;
            default:
                log.info("Unknown event type: {}", message.getEventType());
        }
    }

    /**
     * 알림 처리
     */
    private void processNotification(String message) {
        // 실제 알림 전송 로직 구현
        log.info("Processing notification: {}", message);
    }
}
