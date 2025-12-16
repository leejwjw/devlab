package com.example.devlab.service;

import com.example.devlab.domain.Product;
import com.example.devlab.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Product 비즈니스 로직 서비스
 * ElasticSearch를 사용한 제품 관리 기능
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 제품 생성
     */
    public Product createProduct(Product product) {
        log.info("Creating product: {}", product.getName());
        return productRepository.save(product);
    }

    /**
     * 제품 조회
     */
    public Optional<Product> getProduct(String id) {
        log.info("Getting product by id: {}", id);
        return productRepository.findById(id);
    }

    /**
     * 모든 제품 조회
     */
    public List<Product> getAllProducts() {
        log.info("Getting all products");
        Iterable<Product> products = productRepository.findAll();
        return (List<Product>) products;
    }

    /**
     * 제품명으로 검색
     */
    public List<Product> searchByName(String name) {
        log.info("Searching products by name: {}", name);
        return productRepository.findByNameContaining(name);
    }

    /**
     * 카테고리로 검색
     */
    public List<Product> searchByCategory(String category) {
        log.info("Searching products by category: {}", category);
        return productRepository.findByCategory(category);
    }

    /**
     * 브랜드로 검색
     */
    public List<Product> searchByBrand(String brand) {
        log.info("Searching products by brand: {}", brand);
        return productRepository.findByBrand(brand);
    }

    /**
     * 가격 범위로 검색
     */
    public List<Product> searchByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        log.info("Searching products by price range: {} - {}", minPrice, maxPrice);
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    /**
     * 제품 업데이트
     */
    public Product updateProduct(Product product) {
        log.info("Updating product: {}", product.getId());
        return productRepository.save(product);
    }

    /**
     * 제품 삭제
     */
    public void deleteProduct(String id) {
        log.info("Deleting product: {}", id);
        productRepository.deleteById(id);
    }
}
