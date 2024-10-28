package com.freshkit.webproject.product.service;

import com.freshkit.webproject.product.dto.ProductDto;
import com.freshkit.webproject.product.mapper.ProductMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.freshkit.webproject.util.JsonUtil;
@Service
public class ProductService {

    private final ProductMapper productMapper;
    private final JsonUtil jsonUtil;
    private List<ProductDto> products;

    @Autowired
    public ProductService(ProductMapper productMapper, JsonUtil jsonUtil) {
        this.productMapper = productMapper;
        this.jsonUtil = jsonUtil;
    }

    @PostConstruct
    public void init() {
        try {
            this.products = productMapper.getProducts();
            if (this.products.isEmpty()) {
                this.products = jsonUtil.readProductsFromJson();
                // 로그 추가
                System.out.println("Products loaded from JSON: " + products.size());
            }
        } catch (IOException e) {
            throw new IllegalStateException("초기화 중 오류 발생", e);
        }
    }

    public List<ProductDto> getProducts() {
        return products;
    }
    // 페이지 전체리스트
    public Optional<ProductDto> getProductById(Long id) {
        // 로그 추가
        System.out.println("Searching product with ID: " + id);
        ProductDto product = productMapper.getProductById(id);
        if (product == null) {
            product = products.stream()
                    .filter(p -> p.getProductId().equals(id))
                    .findFirst()
                    .orElse(null);
        }
        // 로그 추가
        System.out.println("Product found: " + (product != null));
        return Optional.ofNullable(product);
    }


}
