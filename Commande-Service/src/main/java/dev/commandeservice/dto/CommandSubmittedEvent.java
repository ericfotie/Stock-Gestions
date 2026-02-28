package dev.commandeservice.dto;

public record CommandSubmittedEvent(
        Long userId,
        String product,
        Integer quantity
) {
}
