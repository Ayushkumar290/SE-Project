package se.RBC.BloodDonationSecurity.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import se.RBC.BloodDonationSecurity.Security.AuthEntryPointJwt;
import se.RBC.BloodDonationSecurity.Security.AuthTokenFilter;

@Configuration
@EnableWebSecurity
public class ResourceServerSecurityConfig {
    @Autowired
    private AuthTokenFilter authTokenFilter;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler; // Assuming you implement this simple 401 handler

    @Bean
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())

                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated() // All other endpoints require a token
                );

        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
