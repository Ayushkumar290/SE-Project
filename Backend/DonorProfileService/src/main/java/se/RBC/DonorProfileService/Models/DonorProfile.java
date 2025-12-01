package se.RBC.DonorProfileService.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "donor_profiles")
public class DonorProfile {


    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    private String firstName;
    private String lastName;

    // Medical Details
    private String bloodType; // e.g., A_POSITIVE
    private LocalDate dateOfBirth;
    private Double weightKg;
    private String eligibilityStatus; // e.g., ELIGIBLE, TEMPORARILY_DEFERRED
    private LocalDate lastDonationDate;

    // Contact Info (redundant but useful for profile management)
    private String phoneNumber;
    private String primaryClinicId;

}
