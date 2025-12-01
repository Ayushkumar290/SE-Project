package se.RBC.UserMangmentService.Repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.repository.MongoRepository;
import se.RBC.UserMangmentService.Models.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    boolean existsByUsername(@NotBlank String username);

    boolean existsByEmail(@NotBlank @Email String email);
}
