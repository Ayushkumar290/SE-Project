package se.RBC.AppointmentService.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.RBC.AppointmentService.Components.AppointmentMessageProducer;
import se.RBC.AppointmentService.Model.Appointment;
import se.RBC.AppointmentService.Model.DonorProfile;
import se.RBC.AppointmentService.Repository.AppointmentRepository;
import se.RBC.AppointmentService.Client.DonorProfileClient;
@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository repository;
    @Autowired private DonorProfileClient profileClient;
    @Autowired private AppointmentMessageProducer messageProducer;

    public Appointment scheduleAppointment(Appointment appointment, String jwtToken, String userId) {

        // 1. SYNCHRONOUS CHECK: Get Donor Profile Details
        DonorProfile profile = profileClient.getDonorProfile(jwtToken, userId)
                .orElseThrow(() -> new IllegalArgumentException("Donor Profile not found."));

        if (!"ELIGIBLE".equals(profile.getEligibilityStatus())) {
            throw new IllegalStateException("Donor is not eligible: " + profile.getEligibilityStatus());
        }

        // 2. PERSISTENCE
        appointment.setUserId(userId);
        appointment.setStatus("SCHEDULED");
        Appointment newAppointment = repository.save(appointment);

        // 3. ASYNCHRONOUS EVENT
        messageProducer.sendAppointmentCreatedEvent(newAppointment);

        return newAppointment;
    }
}
