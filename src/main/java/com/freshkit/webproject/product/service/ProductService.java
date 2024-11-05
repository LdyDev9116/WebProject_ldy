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

    /**
     * 애플리케이션 시작 시 초기화 메서드.
     * 데이터베이스에서 제품 목록을 불러오고, 없으면 JSON 파일에서 불러옵니다.
     */
    @PostConstruct
    public void init() {
        try {
            this.products = productMapper.getProducts(); // 데이터베이스에서 제품 목록을 가져옴
            if (this.products.isEmpty()) {
                this.products = jsonUtil.readProductsFromJson(); // 데이터가 없으면 JSON에서 로드
                System.out.println("Products loaded from JSON: " + products.size());
            }
        } catch (IOException e) {
            throw new IllegalStateException("초기화 중 오류 발생", e); // JSON 파일 읽기 실패 시 예외 발생
        }
    }

    /**
     * 모든 제품 목록을 반환하는 메서드.
     * @return List<ProductDto> 형태의 제품 목록
     */
    public List<ProductDto> getProducts() {
        return products;
    }

    /**
     * 주어진 ID로 특정 제품을 조회하는 메서드.
     * 데이터베이스에서 먼저 찾고, 없으면 로컬 JSON 데이터를 검색합니다.
     * @param id 검색할 제품의 ID
     * @return Optional<ProductDto> 형태의 제품 데이터 (존재하지 않으면 빈 값 반환)
     */
    public Optional<ProductDto> getProductById(Long id) {
        System.out.println("Searching product with ID: " + id); // 검색 로그 출력

        // 데이터베이스에서 제품 검색
        ProductDto product = productMapper.getProductById(id);

        // 데이터베이스에 없을 경우 JSON에서 불러온 데이터에서 검색
        if (product == null) {
            product = products.stream()
                    .filter(p -> p.getProductId().equals(id))
                    .findFirst()
                    .orElse(null);
        }

        System.out.println("Product found: " + (product != null)); // 검색 성공 여부 로그 출력
        return Optional.ofNullable(product);
    }
}