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
    private List<ProductDto> products; // 초기화된 제품 목록을 저장하는 필드

    @Autowired
    public ProductService(ProductMapper productMapper, JsonUtil jsonUtil) {
        this.productMapper = productMapper;
        this.jsonUtil = jsonUtil;
    }

    /**
     * 애플리케이션 시작 시 호출되는 초기화 메서드입니다.
     * - 데이터베이스에서 제품 목록을 가져옵니다.
     * - 데이터베이스에 제품 데이터가 없으면 JSON 파일에서 데이터를 로드합니다.
     *
     * @throws IllegalStateException JSON 데이터를 로드하는 중 오류가 발생한 경우
     */
    @PostConstruct
    public void init() {
        try {
            // 데이터베이스에서 제품 목록 가져오기
            this.products = productMapper.getProducts();

            // 데이터베이스에 데이터가 없으면 JSON에서 로드
            if (this.products.isEmpty()) {
                this.products = jsonUtil.readProductsFromJson();
                System.out.println("Products loaded from JSON: " + products.size());
            }
        } catch (IOException e) {
            // JSON 데이터 로드 실패 시 예외 발생
            throw new IllegalStateException("초기화 중 오류 발생", e);
        }
    }

    /**
     * 모든 제품 목록을 반환합니다.
     *
     * @return List<ProductDto> 형태의 제품 목록
     */
    public List<ProductDto> getProducts() {
        return products;
    }

    /**
     * 주어진 ID로 특정 제품을 조회합니다.
     * - 데이터베이스에서 먼저 제품을 검색합니다.
     * - 데이터베이스에서 찾을 수 없는 경우, JSON에서 로드된 데이터에서 검색합니다.
     *
     * @param id 검색할 제품의 ID
     * @return Optional<ProductDto> 형태의 제품 데이터.
     *         제품이 존재하지 않으면 빈 Optional 객체 반환.
     */
    public Optional<ProductDto> getProductById(Long id) {
        System.out.println("Searching product with ID: " + id); // 검색 로그 출력

        // 데이터베이스에서 제품 검색
        ProductDto product = productMapper.getProductById(id);

        // 데이터베이스에서 찾지 못한 경우, 로컬 JSON 데이터를 검색
        if (product == null) {
            product = products.stream()
                    .filter(p -> p.getProductId().equals(id))
                    .findFirst()
                    .orElse(null);
        }

        // 검색 결과 로그 출력
        System.out.println("Product found: " + (product != null));
        return Optional.ofNullable(product);
    }
}
