package dev.commandeservice.dto;

public record ProductCheckEvent(
        Long commandId,
        String product,
        Integer quantity
) {
}
