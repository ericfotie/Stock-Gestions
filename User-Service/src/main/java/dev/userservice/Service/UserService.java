package dev.userservice.Service;

import dev.userservice.dto.CommandSubmittedEvent;
import dev.userservice.dto.UserRequestDto;
import dev.userservice.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto save(UserRequestDto userRequestDto);

    UserResponseDto update(Long id, UserRequestDto userRequestDto);

    void delete(Long id);

    UserResponseDto getById(Long id);

    List<UserResponseDto> getAll();

    List<UserResponseDto> getByRole(String role);

    void submitCommand(CommandSubmittedEvent dto);
}
