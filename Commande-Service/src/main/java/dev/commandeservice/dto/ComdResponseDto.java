package dev.commandeservice.dto;

import dev.commandeservice.Model.CommandStatus;

public record ComdResponseDto(
        Long id,
        Long userId,
        String product,
        Integer quantity,
        CommandStatus status
) {
}
