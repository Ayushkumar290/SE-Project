package se.RBC.AppointmentService.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class DonorProfile {
    @Id
    private String id;


    @Indexed(unique = true)
    private String userId;

    private String firstName;
    private String lastName;

    // Medical Details for Eligibility
    private String bloodType; // e.g., A_POSITIVE, O_NEGATIVE
    private LocalDate dateOfBirth;
    private Double weightKg;


    private String eligibilityStatus; // e.g., ELIGIBLE, TEMPORARILY_DEFERRED, INELIGIBLE
    private LocalDate lastDonationDate;

    // Contact Info (Optional, may be copied from User Management)
    private String phoneNumber;
    private String primaryClinicId;
}
