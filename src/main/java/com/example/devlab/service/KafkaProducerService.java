package com.example.devlab.service;

import com.example.devlab.dto.EventMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

/**
 * Kafka Producer 서비스 (Spring Boot 2.x 호환)
 * 이벤트 메시지 발행
 * (IDE 갱신용 업데이트)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 이벤트 메시지 발행
     */
    public void sendEvent(String eventType, String payload) {
        EventMessage message = EventMessage.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(eventType)
                .payload(payload)
                .timestamp(System.currentTimeMillis())
                .build();

        log.info("Sending event: {}", message);

        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("events", message.getEventId(),
                message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("Event sent successfully: {}, offset: {}",
                        message.getEventId(), result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Failed to send event: {}", message.getEventId(), ex);
            }
        });
    }

    /**
     * 알림 메시지 발행
     */
    public void sendNotification(String message) {
        log.info("Sending notification: {}", message);

        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("notifications", message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("Notification sent successfully, offset: {}",
                        result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Failed to send notification", ex);
            }
        });
    }
}
