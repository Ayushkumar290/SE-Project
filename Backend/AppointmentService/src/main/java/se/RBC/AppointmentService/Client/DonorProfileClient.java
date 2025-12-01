package se.RBC.AppointmentService.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import se.RBC.AppointmentService.Model.DonorProfile;

import java.util.Optional;

@Service
public class DonorProfileClient {
    @Autowired
    private WebClient donorProfileWebClient;


    public Optional<DonorProfile> getDonorProfile(String jwtToken, String userId) {

        // Log the attempt for tracking
        System.out.println("Attempting to fetch profile for user: " + userId);

        try {
            // WebClient makes the request. The base URL (http://localhost:8082)
            // is configured in the AppointmentServiceSecurityConfig.
            DonorProfile profile = donorProfileWebClient.get()
                    // Use the shared 'me' endpoint
                    .uri("/profiles/me")
                    // Add the JWT token to authorize the request
                    .header("Authorization", "Bearer " + jwtToken)
                    .retrieve()
                    .bodyToMono(DonorProfile.class)
                    .block(); // Blocks until the response is received (synchronous behavior)

            return Optional.ofNullable(profile);

        } catch (WebClientResponseException.NotFound e) {
            // 404 means the profile has not been created in the Donor Profile Service yet
            System.err.println("Donor Profile not found (404) for user: " + userId);
            return Optional.empty();
        } catch (WebClientResponseException e) {
            // Handle other errors (e.g., 401/403 if the token is invalid or expired)
            System.err.println("Error accessing Donor Profile Service: " + e.getMessage());
            throw new RuntimeException("Secure access to Donor Profile failed.", e);
        }
    }
}
