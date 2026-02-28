package dev.productservice.dto;

public record ProductRequestDto(
        String name,
        Integer stock
) {
}
