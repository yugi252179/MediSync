package com.yugi.medicare.controller;

import com.yugi.medicare.entity.Appointment;
import com.yugi.medicare.entity.Patient;
import com.yugi.medicare.entity.Prescription;
import com.yugi.medicare.service.AppointmentService;
import com.yugi.medicare.service.PatientService;
import com.yugi.medicare.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@CrossOrigin
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @GetMapping("/mobile/{mobile}")
    public Patient getPatientByMobile(@PathVariable String mobile) {
        return patientService.getPatientByMobile(mobile);
    }

    @GetMapping("/{id}/appointments")
    public List<Appointment> getPatientAppointments(@PathVariable Long id) {
        return appointmentService.getAppointmentsByPatient(id);
    }

    @GetMapping("/{id}/prescriptions")
    public List<Prescription> getPatientPrescriptions(@PathVariable Long id) {
        return prescriptionService.getPrescriptionsByPatient(id);
    }
}
