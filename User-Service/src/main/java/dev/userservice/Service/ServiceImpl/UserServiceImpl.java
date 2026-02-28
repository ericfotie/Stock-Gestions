package dev.userservice.Service.ServiceImpl;

import dev.userservice.Mapper.UserMapper;
import dev.userservice.Modele.Role;
import dev.userservice.Modele.UserModel;
import dev.userservice.Producer.UserProducer;
import dev.userservice.Repository.UserRepository;
import dev.userservice.Service.UserService;
import dev.userservice.dto.CommandSubmittedEvent;
import dev.userservice.dto.UserRequestDto;
import dev.userservice.dto.UserResponseDto;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProducer userProducer;

    public UserServiceImpl(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }


    @Override
    public UserResponseDto save(UserRequestDto userRequestDto) {
        UserModel user = UserMapper.toEntity(userRequestDto);
        UserModel saved = userRepository.save(user);
        log.info("User créé avec ID {}", saved.getId());
        return UserMapper.toDto(saved);
    }


    @Override
    public UserResponseDto update(Long id, UserRequestDto userRequestDto) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User non trouvé"));


        UserMapper.updateEntity(user, userRequestDto);

        UserModel updated = userRepository.save(user);
        log.info("User mis à jour avec ID {}", updated.getId());
        return UserMapper.toDto(updated);
    }


    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User non trouvé pour suppression");
        }
        userRepository.deleteById(id);
        log.info("User supprimé avec ID {}", id);
    }


    @Override
    public UserResponseDto getById(Long id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User non trouvé"));
        return UserMapper.toDto(user);
    }


    @Override
    public List<UserResponseDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }


    @Override
    public List<UserResponseDto> getByRole(String role) {
        Role userRole;
        try {
            userRole = Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Role invalide : " + role);
        }

        return userRepository.findByRole(userRole)
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public void submitCommand(CommandSubmittedEvent dto) {
        userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User non trouvé pour soumission de commande"));


        CommandSubmittedEvent event = new CommandSubmittedEvent(
                dto.userId(),
                dto.product(),
                dto.quantity()
        );


        userProducer.publish(event);

        log.info("Commande envoyée au command-service pour user ID {}", dto.userId());

    }

}