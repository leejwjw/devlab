package com.example.devlab.repository;

import com.example.devlab.domain.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * ElasticSearch Product Repository
 * Spring Data ElasticSearch를 사용한 CRUD 및 검색 기능
 */
@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, String> {

    /**
     * 제품명으로 검색
     */
    List<Product> findByNameContaining(String name);

    /**
     * 카테고리로 검색
     */
    List<Product> findByCategory(String category);

    /**
     * 브랜드로 검색
     */
    List<Product> findByBrand(String brand);

    /**
     * 가격 범위로 검색
     */
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * 재고가 있는 제품 검색
     */
    List<Product> findByStockGreaterThan(Integer stock);
}
