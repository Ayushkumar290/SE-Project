package se.RBC.AppointmentService.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;
@Data
@Getter
@Setter
@Document(collation = "appointments")
public class Appointment {
    @Id
    private String id;
    private String userId; // User ID extracted from the JWT
    private String clinicId;
    private LocalDateTime appointmentTime;
    private String status;
}
