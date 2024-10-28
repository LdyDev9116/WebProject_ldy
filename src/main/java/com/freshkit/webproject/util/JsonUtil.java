package com.freshkit.webproject.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freshkit.webproject.product.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class JsonUtil {

    private final ObjectMapper objectMapper;

    @Autowired
    public JsonUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<ProductDto> readProductsFromJson() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.json")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: products.json");
            }
            return objectMapper.readValue(inputStream, new TypeReference<List<ProductDto>>() {});
        }
    }
}
