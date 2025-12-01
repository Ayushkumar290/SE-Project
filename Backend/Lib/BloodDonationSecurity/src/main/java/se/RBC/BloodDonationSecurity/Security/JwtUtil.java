package se.RBC.BloodDonationSecurity.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret.key}")
    private String jwtSecret;
    @Value("${jwt.expiration.ms}")
    private long jwtExpirationMs;

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }


    public String generateToken(Authentication authentication) {

        // Get the Principal object (UserDetails implementation)
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername()) // Set subject (typically the user ID)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Set expiration time
                .signWith(key(), SignatureAlgorithm.HS512) // Sign with the secret key
                .compact();
    }
    public String extractUsername(String jwt) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(jwt).getBody().getSubject();
    }

    //Validates the signature and expiration.
    public boolean validateToken(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            // In a resource server, we only need to catch and suppress exceptions;
            // the filter will handle the 401 response.
            return false;
        }
    }
}
