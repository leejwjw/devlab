package com.example.devlab.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Kafka 이벤트 메시지 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventMessage implements Serializable {

    private String eventId;
    private String eventType;
    private String payload;
    private Long timestamp;
}
