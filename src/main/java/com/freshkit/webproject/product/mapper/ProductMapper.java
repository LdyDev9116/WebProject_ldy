package com.freshkit.webproject.product.mapper;

import com.freshkit.webproject.product.dto.ProductDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<ProductDto> getProducts();

    ProductDto getProductById(Long id);
}
