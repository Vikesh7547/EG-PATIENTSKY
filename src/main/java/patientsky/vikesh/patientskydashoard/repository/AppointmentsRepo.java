package patientsky.vikesh.patientskydashoard.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import patientsky.vikesh.patientskydashoard.models.Appointment;


@Repository
public interface AppointmentsRepo extends JpaRepository<Appointment, String>{
    
    List<Appointment> findAllByCalendarIdAndStartTimeBetween(String calendarId, LocalDateTime startTime, LocalDateTime endTime);
    
    
}