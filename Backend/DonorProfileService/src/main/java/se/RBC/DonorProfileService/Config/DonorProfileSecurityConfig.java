package se.RBC.DonorProfileService.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import se.RBC.BloodDonationSecurity.Config.ResourceServerSecurityConfig;

@Configuration
@EnableWebSecurity
@Import(ResourceServerSecurityConfig.class)
public class DonorProfileSecurityConfig {
}
