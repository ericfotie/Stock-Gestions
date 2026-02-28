package dev.commandeservice.dto;

import dev.commandeservice.Model.CommandStatus;

public record ComdRequestDto(
        Long userId,
        String product,
        Integer quantity,
        CommandStatus status
) {
}
