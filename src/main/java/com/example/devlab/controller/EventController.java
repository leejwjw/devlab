package com.example.devlab.controller;

import com.example.devlab.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Event REST API Controller
 * Kafka 이벤트 발행 테스트용 API
 */
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final KafkaProducerService kafkaProducerService;

    /**
     * 이벤트 발행
     */
    @PostMapping
    public ResponseEntity<String> publishEvent(
            @RequestParam String eventType,
            @RequestParam String payload) {
        kafkaProducerService.sendEvent(eventType, payload);
        return ResponseEntity.ok("Event published successfully");
    }

    /**
     * 알림 발행
     */
    @PostMapping("/notifications")
    public ResponseEntity<String> publishNotification(@RequestParam String message) {
        kafkaProducerService.sendNotification(message);
        return ResponseEntity.ok("Notification published successfully");
    }
}
