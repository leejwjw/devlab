package com.example.devlab.controller;

import com.example.devlab.service.MetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Metrics REST API Controller
 * 메트릭 테스트용 API
 */
@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
public class MetricsController {

    private final MetricsService metricsService;

    /**
     * API 호출 기록 테스트
     */
    @GetMapping("/test")
    public ResponseEntity<String> testMetrics() {
        metricsService.recordApiCall();
        return ResponseEntity.ok("Metrics recorded");
    }

    /**
     * 비즈니스 작업 수행 (메트릭 자동 기록)
     */
    @PostMapping("/operation")
    public ResponseEntity<String> performOperation(@RequestParam String operation) {
        try {
            String result = metricsService.performBusinessOperation(operation);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Operation failed: " + e.getMessage());
        }
    }

    /**
     * 활성 사용자 증가
     */
    @PostMapping("/users/join")
    public ResponseEntity<Integer> userJoin() {
        metricsService.incrementActiveUsers();
        return ResponseEntity.ok(metricsService.getActiveUsers());
    }

    /**
     * 활성 사용자 감소
     */
    @PostMapping("/users/leave")
    public ResponseEntity<Integer> userLeave() {
        metricsService.decrementActiveUsers();
        return ResponseEntity.ok(metricsService.getActiveUsers());
    }

    /**
     * 활성 사용자 수 조회
     */
    @GetMapping("/users/active")
    public ResponseEntity<Integer> getActiveUsers() {
        return ResponseEntity.ok(metricsService.getActiveUsers());
    }
}
