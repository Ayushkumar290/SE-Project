package se.RBC.AppointmentService.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import se.RBC.AppointmentService.Model.Appointment;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment,String> {

}
