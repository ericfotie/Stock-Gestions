package dev.productservice.dto;

public record ProductResponseDto(
        Long id,
        String name,
        Integer stock
) {
}
