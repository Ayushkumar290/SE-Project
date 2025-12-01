package se.RBC.AppointmentService.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.RBC.AppointmentService.Model.Appointment;
import se.RBC.AppointmentService.Services.AppointmentService;

import java.security.Principal;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(
            @RequestBody Appointment request,
            Principal principal, // Gets authenticated user ID from JWT
            @RequestHeader("Authorization") String authHeader) {

        String userId = principal.getName();
        // Extract token by removing "Bearer " prefix (first 7 characters)
        String jwtToken = authHeader.substring(7);

        try {
            Appointment created = appointmentService.scheduleAppointment(request, jwtToken, userId);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalArgumentException | IllegalStateException e) {
            // Catch errors like profile not found or ineligible status
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
