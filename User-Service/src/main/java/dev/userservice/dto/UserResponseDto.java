package dev.userservice.dto;

public record UserResponseDto(
        long id,
        String nom,
        String email,
        String role
) {
}
