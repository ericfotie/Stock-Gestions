package dev.userservice.dto;

public record CommandSubmittedEvent(
        Long userId,
        String product,
        int quantity
) {
}
