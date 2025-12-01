package se.RBC.UserMangmentService.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import se.RBC.BloodDonationSecurity.Security.JwtUtil;
import se.RBC.UserMangmentService.DTO.Request.LoginRequest;
import se.RBC.UserMangmentService.DTO.Request.RegisterRequest;
import se.RBC.UserMangmentService.DTO.Response.JwtResponse;
import se.RBC.UserMangmentService.DTO.Response.MessageResponse;
import se.RBC.UserMangmentService.Enum.UserRole;
import se.RBC.UserMangmentService.Impl.UserDetailsImpl;
import se.RBC.UserMangmentService.Models.User;
import se.RBC.UserMangmentService.Repository.UserRepository;


import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // FIX: Complete the roles extraction and return the response
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {

        // 1. Check if Username is already taken
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // 2. Check if Email is already in use
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // 3. Create new User entity
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());

        // 4. Encode Password (CRITICAL SECURITY STEP)
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        // 5. Assign Default Role (e.g., DONOR)
        user.getRoles().add(UserRole.DONOR);

        // NOTE: If you need to link to the Donor Profile Service later,
        // you might save the user here and then publish an event. For now, we just save.

        // 6. Save User to MongoDB
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


}
