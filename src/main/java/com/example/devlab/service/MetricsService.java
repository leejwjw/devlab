package com.example.devlab.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 메트릭 수집 서비스
 * 비즈니스 로직에서 메트릭 기록
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MetricsService {

    private final Counter apiCallCounter;
    private final Counter errorCounter;
    private final AtomicInteger activeUsers;
    private final Timer apiResponseTimer;

    /**
     * API 호출 기록
     */
    public void recordApiCall() {
        apiCallCounter.increment();
        log.debug("API call recorded");
    }

    /**
     * 에러 발생 기록
     */
    public void recordError() {
        errorCounter.increment();
        log.debug("Error recorded");
    }

    /**
     * 활성 사용자 증가
     */
    public void incrementActiveUsers() {
        int count = activeUsers.incrementAndGet();
        log.debug("Active users incremented: {}", count);
    }

    /**
     * 활성 사용자 감소
     */
    public void decrementActiveUsers() {
        int count = activeUsers.decrementAndGet();
        log.debug("Active users decremented: {}", count);
    }

    /**
     * 활성 사용자 수 조회
     */
    public int getActiveUsers() {
        return activeUsers.get();
    }

    /**
     * API 응답 시간 기록
     */
    public void recordResponseTime(Runnable task) {
        apiResponseTimer.record(task);
    }

    /**
     * 시뮬레이션: 비즈니스 작업 수행 및 메트릭 기록
     */
    public String performBusinessOperation(String operation) {
        recordApiCall();

        try {
            return apiResponseTimer.recordCallable(() -> {
                log.info("Performing operation: {}", operation);

                try {
                    // 비즈니스 로직 시뮬레이션
                    Thread.sleep((long) (Math.random() * 1000));

                    // 임의로 에러 발생
                    if (Math.random() < 0.1) {
                        recordError();
                        throw new RuntimeException("Simulated error");
                    }

                    return "Operation completed: " + operation;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    recordError();
                    throw new RuntimeException("Operation interrupted", e);
                }
            });
        } catch (Exception e) {
            log.error("Operation failed", e);
            throw new RuntimeException("Business operation failed", e);
        }
    }
}
