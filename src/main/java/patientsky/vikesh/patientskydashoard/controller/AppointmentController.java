package patientsky.vikesh.patientskydashoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import patientsky.vikesh.patientskydashoard.models.Appointment;
import patientsky.vikesh.patientskydashoard.services.AppointmentService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    
    
    @PostMapping("/appointments")
    public ResponseEntity<String> saveAppointments(@RequestBody List<Appointment> appointments) {
        try {
            appointmentService.saveAppointments(appointments);
            return ResponseEntity.ok("Appointments saved successfully");
        } catch (Exception e) {
            // Handle the exception, log, or return an error response
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving appointments");
        }
    }
    
   
    @GetMapping("/availableslots")
    public ResponseEntity<List<Map<String, String>>> getAvailableSlots(
            @RequestParam String calendarId,
            @RequestParam Integer duration,
            @RequestParam String startTime,
            @RequestParam String endTime
    ) {
        try {
            LocalDateTime startDateTime = LocalDateTime.parse(startTime);
            LocalDateTime endDateTime = LocalDateTime.parse(endTime);
    
            // Find available slots using the service
            List<Map<String, String>> availableSlots = appointmentService.findAvailableSlots(calendarId, duration, startDateTime, endDateTime);
    
            // Prepare the response message based on available slots
            String message = availableSlots.isEmpty() ? "no slots available" : "requested slots are available for given period";
            Map<String, String> result = new HashMap<>();
            result.put("message", message);
            availableSlots.add(result);
    
            return ResponseEntity.ok(availableSlots);
        } catch (Exception e) {
            // Handle the exception, log, or return an error response
            e.printStackTrace();
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }

}
