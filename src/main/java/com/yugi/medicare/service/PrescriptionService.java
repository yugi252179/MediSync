package com.yugi.medicare.service;

import com.yugi.medicare.dto.PrescriptionRequest;
import com.yugi.medicare.entity.Appointment;
import com.yugi.medicare.entity.AppointmentStatus;
import com.yugi.medicare.entity.Prescription;
import com.yugi.medicare.exception.ResourceNotFoundException;
import com.yugi.medicare.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Transactional
    public Prescription createPrescription(PrescriptionRequest request) {
        if (request.getAppointmentId() == null) {
            throw new IllegalArgumentException("Appointment ID must be provided");
        }

        Appointment appointment = appointmentService.getAppointmentById(request.getAppointmentId());

        Prescription prescription = new Prescription();
        prescription.setAppointment(appointment);
        prescription.setDiagnosis(request.getDiagnosis());
        prescription.setMedicines(request.getMedicines());

        // Automatically update appointment status to COMPLETED
        appointmentService.updateAppointmentStatus(appointment.getId(), AppointmentStatus.COMPLETED);

        return prescriptionRepository.save(prescription);
    }

    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found with ID: " + id));
    }

    public Prescription getPrescriptionByAppointment(Long appointmentId) {
        return prescriptionRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found for Appointment ID: " + appointmentId));
    }

    public List<Prescription> getPrescriptionsByPatient(Long patientId) {
        return prescriptionRepository.findByAppointmentPatientId(patientId);
    }
}
