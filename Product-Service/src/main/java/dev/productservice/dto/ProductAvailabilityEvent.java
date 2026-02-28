package dev.productservice.dto;

public record ProductAvailabilityEvent(
        Long commandId,
        String product,
        boolean available
) {
}
