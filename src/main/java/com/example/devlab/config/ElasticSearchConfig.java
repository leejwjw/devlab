package com.example.devlab.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * ElasticSearch 설정 클래스
 * Spring Data ElasticSearch Repository 활성화
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.devlab.repository")
public class ElasticSearchConfig {
    // Spring Boot의 자동 설정을 사용
    // application.yaml에서 연결 정보 설정
}
