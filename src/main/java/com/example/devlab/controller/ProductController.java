package com.example.devlab.controller;

import com.example.devlab.domain.Product;
import com.example.devlab.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Product REST API Controller
 * ElasticSearch를 사용한 제품 관리 API
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 제품 생성
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product created = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * 제품 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        return productService.getProduct(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 모든 제품 조회
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * 제품명으로 검색
     */
    @GetMapping("/search/name")
    public ResponseEntity<List<Product>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchByName(name));
    }

    /**
     * 카테고리로 검색
     */
    @GetMapping("/search/category")
    public ResponseEntity<List<Product>> searchByCategory(@RequestParam String category) {
        return ResponseEntity.ok(productService.searchByCategory(category));
    }

    /**
     * 브랜드로 검색
     */
    @GetMapping("/search/brand")
    public ResponseEntity<List<Product>> searchByBrand(@RequestParam String brand) {
        return ResponseEntity.ok(productService.searchByBrand(brand));
    }

    /**
     * 가격 범위로 검색
     */
    @GetMapping("/search/price")
    public ResponseEntity<List<Product>> searchByPriceRange(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        return ResponseEntity.ok(productService.searchByPriceRange(min, max));
    }

    /**
     * 제품 업데이트
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String id,
            @RequestBody Product product) {
        product.setId(id);
        Product updated = productService.updateProduct(product);
        return ResponseEntity.ok(updated);
    }

    /**
     * 제품 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
