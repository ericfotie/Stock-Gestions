package dev.userservice.Repository;

import dev.userservice.Modele.Role;
import dev.userservice.Modele.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserModel ,Long> {

    List<UserModel> findByRole(Role role);
}