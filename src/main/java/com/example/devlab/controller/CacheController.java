package com.example.devlab.controller;

import com.example.devlab.dto.CacheData;
import com.example.devlab.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * Cache REST API Controller
 * Redis 캐시 테스트용 API
 */
@RestController
@RequestMapping("/api/cache")
@RequiredArgsConstructor
public class CacheController {

    private final CacheService cacheService;

    /**
     * 캐시 조회 (Spring Cache)
     */
    @GetMapping("/{key}")
    public ResponseEntity<CacheData> getCachedData(@PathVariable String key) {
        return ResponseEntity.ok(cacheService.getCachedData(key));
    }

    /**
     * 캐시 업데이트 (Spring Cache)
     */
    @PutMapping
    public ResponseEntity<CacheData> updateCache(@RequestBody CacheData data) {
        return ResponseEntity.ok(cacheService.updateCache(data));
    }

    /**
     * 캐시 삭제 (Spring Cache)
     */
    @DeleteMapping("/{key}")
    public ResponseEntity<Void> evictCache(@PathVariable String key) {
        cacheService.evictCache(key);
        return ResponseEntity.noContent().build();
    }

    /**
     * 모든 캐시 삭제 (Spring Cache)
     */
    @DeleteMapping
    public ResponseEntity<Void> evictAllCache() {
        cacheService.evictAllCache();
        return ResponseEntity.noContent().build();
    }

    /**
     * Redis에 직접 저장 (RedisTemplate)
     */
    @PostMapping("/redis")
    public ResponseEntity<Void> saveToRedis(
            @RequestParam String key,
            @RequestParam String value,
            @RequestParam(defaultValue = "60") long timeout) {
        cacheService.saveToRedis(key, value, timeout, TimeUnit.SECONDS);
        return ResponseEntity.ok().build();
    }

    /**
     * Redis에서 직접 조회 (RedisTemplate)
     */
    @GetMapping("/redis/{key}")
    public ResponseEntity<Object> getFromRedis(@PathVariable String key) {
        Object value = cacheService.getFromRedis(key);
        if (value == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(value);
    }

    /**
     * Redis Hash 저장
     */
    @PostMapping("/hash/{hashKey}/{field}")
    public ResponseEntity<Void> saveToHash(
            @PathVariable String hashKey,
            @PathVariable String field,
            @RequestParam String value) {
        cacheService.saveToHash(hashKey, field, value);
        return ResponseEntity.ok().build();
    }

    /**
     * Redis Hash 조회
     */
    @GetMapping("/hash/{hashKey}/{field}")
    public ResponseEntity<Object> getFromHash(
            @PathVariable String hashKey,
            @PathVariable String field) {
        Object value = cacheService.getFromHash(hashKey, field);
        if (value == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(value);
    }
}
