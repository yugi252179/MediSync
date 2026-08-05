package com.yugi.medicare.controller;

import com.yugi.medicare.dto.PrescriptionRequest;
import com.yugi.medicare.entity.Appointment;
import com.yugi.medicare.entity.AppointmentStatus;
import com.yugi.medicare.entity.Doctor;
import com.yugi.medicare.entity.Prescription;
import com.yugi.medicare.service.AppointmentService;
import com.yugi.medicare.service.DoctorService;
import com.yugi.medicare.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Doctor")
@CrossOrigin
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/new_doctor")
    public Doctor signup(@RequestBody Doctor doctor) {
        return doctorService.saveDoctor(doctor);
    }

    @GetMapping("/all")
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    @GetMapping("/Queue")
    public List<Appointment> getWaitingPatients() {
        return appointmentService.getWaitingAppointments();
    }

    @GetMapping("/{doctorId}/queue")
    public List<Appointment> getDoctorQueue(@PathVariable Long doctorId) {
        return appointmentService.getQueueForDoctor(doctorId);
    }

    @PostMapping("/prescription")
    public ResponseEntity<Prescription> createPrescription(@RequestBody PrescriptionRequest request) {
        Prescription prescription = prescriptionService.createPrescription(request);
        return ResponseEntity.ok(prescription);
    }

    @PutMapping("/appointment/{id}/status")
    public Appointment updateAppointmentStatus(@PathVariable Long id, @RequestParam AppointmentStatus status) {
        return appointmentService.updateAppointmentStatus(id, status);
    }
}
