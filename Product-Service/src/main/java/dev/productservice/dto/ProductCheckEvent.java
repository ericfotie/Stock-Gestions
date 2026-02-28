package dev.productservice.dto;

public record ProductCheckEvent(
        Long commandId,
        String product,
        Integer quantity
) {
}
