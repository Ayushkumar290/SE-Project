package se.RBC.UserMangmentService.Models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import se.RBC.UserMangmentService.Enum.UserRole;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;


    @Indexed(unique = true)
    private String email;


    private String password;

    private Set<UserRole> roles = new HashSet<>();

    private String donorProfileId;

    private String status;

    public User(@NotBlank String username, @NotBlank @Email String email, String encode) {
    }
}
