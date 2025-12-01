package se.RBC.AppointmentService.Components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import se.RBC.AppointmentService.Model.Appointment;

@Component
public class AppointmentMessageProducer {

    private static final String OUTPUT_BINDING_NAME = "appointment-out-0";

    @Autowired
    private StreamBridge streamBridge;

    public boolean sendAppointmentCreatedEvent(Appointment appointment) {
        return streamBridge.send(OUTPUT_BINDING_NAME, appointment);
    }
}
