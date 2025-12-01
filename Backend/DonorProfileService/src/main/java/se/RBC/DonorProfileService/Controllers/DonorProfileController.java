package se.RBC.DonorProfileService.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.RBC.DonorProfileService.Models.DonorProfile;
import se.RBC.DonorProfileService.Services.DonorProfileService;

import java.security.Principal;

@RestController
@RequestMapping
public class DonorProfileController {

    @Autowired
    private DonorProfileService profileService;
    private String getAuthenticatedUserId(Principal principal) {

        return principal.getName();
    }
    @PostMapping
    public ResponseEntity<DonorProfile> createProfile(
            @RequestBody DonorProfile profile,
            Principal principal) {

        String userId = getAuthenticatedUserId(principal);
        DonorProfile createdProfile = profileService.createProfile(profile, userId);
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }
    @GetMapping("/me")
    public ResponseEntity<DonorProfile> getMyProfile(Principal principal) {
        String userId = getAuthenticatedUserId(principal);

        return profileService.getProfileByUserId(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/me")
    public ResponseEntity<DonorProfile> updateMyProfile(
            @RequestBody DonorProfile profile,
            Principal principal) {

        String userId = getAuthenticatedUserId(principal);
        try {
            DonorProfile updated = profileService.updateProfile(userId, profile);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
