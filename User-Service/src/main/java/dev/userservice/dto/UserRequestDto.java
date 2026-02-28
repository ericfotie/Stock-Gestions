package dev.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRequestDto(
        @NotBlank String nom,
        @NotBlank String email,
        @NotBlank
        @Pattern(regexp = "USER|ADMIN", message = "Role must be USER or ADMIN")
        String role
) {
}
