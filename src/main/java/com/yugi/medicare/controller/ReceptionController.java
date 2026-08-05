package com.yugi.medicare.controller;

import com.yugi.medicare.entity.Appointment;
import com.yugi.medicare.entity.AppointmentStatus;
import com.yugi.medicare.entity.Doctor;
import com.yugi.medicare.entity.Patient;
import com.yugi.medicare.service.AppointmentService;
import com.yugi.medicare.service.DoctorService;
import com.yugi.medicare.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reception")
@CrossOrigin
public class ReceptionController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/appointment")
    public Appointment createAppointment(@RequestBody Appointment request) {
        return appointmentService.createAppointment(request);
    }

    @GetMapping("/appointments")
    public List<Appointment> getAppointments(@RequestParam(required = false) AppointmentStatus status) {
        if (status != null) {
            return appointmentService.getAppointmentsByStatus(status);
        }
        return appointmentService.getAllAppointments();
    }

    @PostMapping("/patient")
    public Patient registerPatient(@RequestBody Patient patient) {
        return patientService.getOrCreatePatient(patient);
    }

    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/doctors")
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @PutMapping("/appointment/{id}/cancel")
    public Appointment cancelAppointment(@PathVariable Long id) {
        return appointmentService.updateAppointmentStatus(id, AppointmentStatus.CANCELLED);
    }
}
