package se.RBC.AppointmentService.Config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.reactive.function.client.WebClient;
import se.RBC.BloodDonationSecurity.Config.ResourceServerSecurityConfig;

@Configuration
@EnableWebSecurity
@Import(ResourceServerSecurityConfig.class)
public class AppointmentServiceSecurityConfig {

    @Value("${donor.profile.service.url}")
    private String donorProfileServiceUrl;

    @Bean
    public WebClient donorProfileWebClient(WebClient.Builder builder) {
        return builder.baseUrl(donorProfileServiceUrl).build();
    }
}
