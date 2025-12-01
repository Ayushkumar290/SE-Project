package se.RBC.BloodDonationSecurity.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        logger.error("Unauthorized access attempt. Error: {}", authException.getMessage());

        // 1. Set the HTTP status to 401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 2. Set the content type to indicate a JSON response
        response.setContentType("application/json");

        // 3. Write a custom JSON error message to the response body
        response.getWriter().write(
                "{\"status\": 401, " +
                        "\"error\": \"Unauthorized\", " +
                        "\"message\": \"" +
                        "Authentication failed: A valid JWT token is required to access this service. Details: " +
                        authException.getMessage() +
                        "\", \"path\": \"" + request.getServletPath() + "\"}"
        );
    }
}
