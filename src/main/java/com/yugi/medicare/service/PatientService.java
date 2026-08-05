package com.yugi.medicare.service;

import com.yugi.medicare.entity.Patient;
import com.yugi.medicare.exception.ResourceNotFoundException;
import com.yugi.medicare.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient getOrCreatePatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient object cannot be null");
        }
        if (patient.getMobile() != null && !patient.getMobile().trim().isEmpty()) {
            return patientRepository.findByMobile(patient.getMobile())
                    .orElseGet(() -> patientRepository.save(patient));
        }
        return patientRepository.save(patient);
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));
    }

    public Patient getPatientByMobile(String mobile) {
        return patientRepository.findByMobile(mobile)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with mobile: " + mobile));
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public List<Patient> searchPatientsByName(String name) {
        return patientRepository.findByNameContainingIgnoreCase(name);
    }
}
