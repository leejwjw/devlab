package com.example.devlab.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Micrometer 메트릭 설정
 * 커스텀 메트릭 정의
 */
@Configuration
public class MetricsConfig {

    /**
     * API 호출 카운터
     */
    @Bean
    public Counter apiCallCounter(MeterRegistry registry) {
        return Counter.builder("api.calls.total")
                .description("Total number of API calls")
                .tag("application", "devlab")
                .register(registry);
    }

    /**
     * 에러 카운터
     */
    @Bean
    public Counter errorCounter(MeterRegistry registry) {
        return Counter.builder("api.errors.total")
                .description("Total number of errors")
                .tag("application", "devlab")
                .register(registry);
    }

    /**
     * 활성 사용자 게이지
     */
    @Bean
    public AtomicInteger activeUsers() {
        return new AtomicInteger(0);
    }

    @Bean
    public Gauge activeUsersGauge(MeterRegistry registry, AtomicInteger activeUsers) {
        return Gauge.builder("users.active", activeUsers, AtomicInteger::get)
                .description("Number of active users")
                .tag("application", "devlab")
                .register(registry);
    }

    /**
     * API 응답 시간 타이머
     */
    @Bean
    public Timer apiResponseTimer(MeterRegistry registry) {
        return Timer.builder("api.response.time")
                .description("API response time")
                .tag("application", "devlab")
                .register(registry);
    }
}
