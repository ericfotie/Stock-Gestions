package dev.userservice.Controller;

import dev.userservice.Service.UserService;
import dev.userservice.dto.CommandSubmittedEvent;
import dev.userservice.dto.UserRequestDto;
import dev.userservice.dto.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserResponseDto> save( @RequestBody UserRequestDto request) {
        UserResponseDto response = userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(
            @PathVariable("id") Long id,
            @RequestBody UserRequestDto request) {

        UserResponseDto response = userService.update(id, request);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        UserResponseDto response = userService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<UserResponseDto> users = userService.getAll();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponseDto>> getByRole(@PathVariable String role) {
        List<UserResponseDto> users = userService.getByRole(role);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/submit-command")
    public ResponseEntity<String> submitCommand( @RequestBody CommandSubmittedEvent dto) {
        userService.submitCommand(dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Commande envoyée au command-service avec succès");
    }
}