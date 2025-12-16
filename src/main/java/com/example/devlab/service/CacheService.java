package com.example.devlab.service;

import com.example.devlab.dto.CacheData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Redis 캐시 서비스
 * Spring Cache Abstraction과 RedisTemplate 사용 예제
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 캐시에서 데이터 조회 (없으면 생성)
     */
    @Cacheable(value = "cacheData", key = "#key")
    public CacheData getCachedData(String key) {
        log.info("Cache miss - generating data for key: {}", key);
        return CacheData.builder()
                .key(key)
                .value("Generated value for " + key)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * 캐시 업데이트
     */
    @CachePut(value = "cacheData", key = "#data.key")
    public CacheData updateCache(CacheData data) {
        log.info("Updating cache for key: {}", data.getKey());
        data.setTimestamp(System.currentTimeMillis());
        return data;
    }

    /**
     * 캐시 삭제
     */
    @CacheEvict(value = "cacheData", key = "#key")
    public void evictCache(String key) {
        log.info("Evicting cache for key: {}", key);
    }

    /**
     * 모든 캐시 삭제
     */
    @CacheEvict(value = "cacheData", allEntries = true)
    public void evictAllCache() {
        log.info("Evicting all cache");
    }

    /**
     * RedisTemplate을 직접 사용한 데이터 저장
     */
    public void saveToRedis(String key, Object value, long timeout, TimeUnit unit) {
        log.info("Saving to Redis - key: {}, timeout: {} {}", key, timeout, unit);
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * RedisTemplate을 직접 사용한 데이터 조회
     */
    public Object getFromRedis(String key) {
        log.info("Getting from Redis - key: {}", key);
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * RedisTemplate을 직접 사용한 데이터 삭제
     */
    public void deleteFromRedis(String key) {
        log.info("Deleting from Redis - key: {}", key);
        redisTemplate.delete(key);
    }

    /**
     * Hash 자료구조 사용 예제
     */
    public void saveToHash(String hashKey, String field, Object value) {
        log.info("Saving to Redis Hash - hash: {}, field: {}", hashKey, field);
        redisTemplate.opsForHash().put(hashKey, field, value);
    }

    /**
     * Hash에서 데이터 조회
     */
    public Object getFromHash(String hashKey, String field) {
        log.info("Getting from Redis Hash - hash: {}, field: {}", hashKey, field);
        return redisTemplate.opsForHash().get(hashKey, field);
    }
}
