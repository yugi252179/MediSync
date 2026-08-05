package com.yugi.medicare.controller;

import com.yugi.medicare.entity.Prescription;
import com.yugi.medicare.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prescription")
@CrossOrigin
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/{id}")
    public Prescription getPrescriptionById(@PathVariable Long id) {
        return prescriptionService.getPrescriptionById(id);
    }

    @GetMapping("/appointment/{appointmentId}")
    public Prescription getPrescriptionByAppointment(@PathVariable Long appointmentId) {
        return prescriptionService.getPrescriptionByAppointment(appointmentId);
    }
}
