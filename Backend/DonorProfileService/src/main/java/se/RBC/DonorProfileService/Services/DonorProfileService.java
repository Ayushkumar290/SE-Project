package se.RBC.DonorProfileService.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import se.RBC.DonorProfileService.Models.DonorProfile;
import se.RBC.DonorProfileService.Repository.DonorProfileRepository;

import java.security.Principal;
import java.util.Optional;

@Service
public class DonorProfileService {
    @Autowired
    private DonorProfileRepository profileRepository;

    public DonorProfile createProfile(DonorProfile profile, String userId) {
        profile.setUserId(userId);
        // Add logic to check if profile already exists for this user ID
        if (profileRepository.findByUserId(userId).isPresent()) {
            throw new IllegalStateException("Profile already exists for user ID: " + userId);
        }
        return profileRepository.save(profile);
    }

    public Optional<DonorProfile> getProfileByUserId(String userId) {
        return profileRepository.findByUserId(userId);
    }
    public DonorProfile updateProfile(String userId, DonorProfile updatedProfile) {
        return profileRepository.findByUserId(userId)
                .map(existingProfile -> {
                    // Only allow updates on fields that are permitted
                    existingProfile.setFirstName(updatedProfile.getFirstName());
                    existingProfile.setBloodType(updatedProfile.getBloodType());
                    // ... update other fields as needed
                    return profileRepository.save(existingProfile);
                })
                .orElseThrow(() -> new RuntimeException("Profile not found for update."));
    }

}
