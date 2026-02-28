package dev.userservice.Mapper;

import dev.userservice.Modele.Role;
import dev.userservice.Modele.UserModel;
import dev.userservice.dto.UserRequestDto;
import dev.userservice.dto.UserResponseDto;

public class UserMapper {


    public static UserModel toEntity(UserRequestDto dto) {
        UserModel user = new UserModel();
        user.setNom(dto.nom());
        user.setEmail(dto.email());


        user.setRole(Role.valueOf(dto.role().toUpperCase().trim()));

        return user;
    }

    public static UserResponseDto toDto(UserModel user) {
        return new UserResponseDto(
                user.getId(),
                user.getNom(),
                user.getEmail(),
                user.getRole().name()
        );
    }


    public static void updateEntity(UserModel user, UserRequestDto dto) {
        user.setNom(dto.nom());
        user.setEmail(dto.email());
        user.setRole(Role.valueOf(dto.role().toUpperCase().trim()));
    }
}