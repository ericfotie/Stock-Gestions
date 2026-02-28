package dev.productservice.Service;

import dev.productservice.dto.ProductRequestDto;
import dev.productservice.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto save(ProductRequestDto dto);
    ProductResponseDto update(Long id, ProductRequestDto dto);
    void delete(Long id);
    ProductResponseDto getById(Long id);
    List<ProductResponseDto> getAll();

    boolean checkAndReduceStock(String productName, int quantity);
}
