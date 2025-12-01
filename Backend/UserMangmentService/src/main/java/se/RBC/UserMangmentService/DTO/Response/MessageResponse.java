package se.RBC.UserMangmentService.DTO.Response;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // For getters, setters, equals, hashCode, and toString
@AllArgsConstructor // For constructor with all arguments
@NoArgsConstructor // For constructor with no arguments
public class MessageResponse {

    private String message;


}