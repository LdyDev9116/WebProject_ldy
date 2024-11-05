package com.freshkit.webproject.product.mapper;

import com.freshkit.webproject.product.dto.ProductDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    /**
     * 모든 제품 목록을 데이터베이스에서 조회하여 반환합니다.
     * @return List<ProductDto> 형태의 제품 목록
     */
    List<ProductDto> getProducts();
    /**
     * 특정 ID에 해당하는 제품을 데이터베이스에서 조회합니다.
     * @param id 검색할 제품의 ID
     * @return ProductDto 형태의 제품 데이터
     */
    ProductDto getProductById(Long id);
}
