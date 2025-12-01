package se.RBC.DonorProfileService.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import se.RBC.DonorProfileService.Models.DonorProfile;

import java.util.Optional;

@Repository
public interface DonorProfileRepository extends MongoRepository<DonorProfile, String> {


    Optional<DonorProfile> findByUserId(String userId);
}
