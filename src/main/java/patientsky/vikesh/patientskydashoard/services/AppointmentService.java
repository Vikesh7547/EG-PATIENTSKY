package patientsky.vikesh.patientskydashoard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import patientsky.vikesh.patientskydashoard.models.Appointment;
import patientsky.vikesh.patientskydashoard.repository.AppointmentsRepo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentsRepo appointmentRepository;

    public void saveAppointments(List<Appointment> appointments) {
        appointmentRepository.saveAll(appointments);
    }

    public List<Map<String, String>> findAvailableSlots(String calendarId, Integer duration, LocalDateTime startTime, LocalDateTime endTime) {
        List<Appointment> appointments = appointmentRepository.findAllByCalendarIdAndStartTimeBetween(
                calendarId, startTime, endTime);

        return calculateAvailableSlots(appointments, duration, startTime, endTime);
    }

    private List<Map<String, String>> calculateAvailableSlots(List<Appointment> appointments, Integer duration, LocalDateTime startTime, LocalDateTime endTime) {
        List<Map<String, String>> availableSlots = new ArrayList<>();
        LocalDateTime currentSlotStart = startTime;

        for (Appointment appointment : appointments) {
            LocalDateTime appointmentStart = appointment.getStartTime();
            LocalDateTime appointmentEnd = appointment.getEndTime();
            System.out.println("time is"+Duration.between(currentSlotStart, appointmentStart).toMinutes());
            if (Duration.between(currentSlotStart, appointmentStart).toMinutes() >= duration) {
                addSlot(availableSlots, currentSlotStart, currentSlotStart.plusMinutes(duration));
            }

            currentSlotStart = appointmentEnd;
        }

        if (Duration.between(currentSlotStart, endTime).toMinutes() >= duration) {
            addSlot(availableSlots, currentSlotStart, currentSlotStart.plusMinutes(duration));
        }

        return availableSlots;
    }

    private void addSlot(List<Map<String, String>> slots, LocalDateTime startTime, LocalDateTime endTime) {
        Map<String, String> slot = new HashMap<>();
        slot.put("startTime", startTime.toString());
        slot.put("endTime", endTime.toString());
        slots.add(slot);
    }
}
