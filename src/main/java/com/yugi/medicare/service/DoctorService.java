package com.yugi.medicare.service;

import com.yugi.medicare.entity.Doctor;
import com.yugi.medicare.exception.ResourceNotFoundException;
import com.yugi.medicare.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor getOrCreateDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor object cannot be null");
        }
        if (doctor.getId() != null) {
            return doctorRepository.findById(doctor.getId())
                    .orElseGet(() -> doctorRepository.save(doctor));
        }
        return doctorRepository.save(doctor);
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + id));
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }
}
