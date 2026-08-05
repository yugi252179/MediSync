package com.yugi.medicare.service;

import com.yugi.medicare.entity.Appointment;
import com.yugi.medicare.entity.AppointmentStatus;
import com.yugi.medicare.entity.Doctor;
import com.yugi.medicare.entity.Patient;
import com.yugi.medicare.exception.ResourceNotFoundException;
import com.yugi.medicare.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    public Appointment createAppointment(Appointment request) {
        if (request.getPatient() == null) {
            throw new IllegalArgumentException("Patient details must be provided");
        }
        if (request.getDoctor() == null) {
            throw new IllegalArgumentException("Doctor details must be provided");
        }

        Patient patient = patientService.getOrCreatePatient(request.getPatient());
        Doctor doctor = doctorService.getOrCreateDoctor(request.getDoctor());

        request.setPatient(patient);
        request.setDoctor(doctor);
        request.setStatus(AppointmentStatus.WAITING);

        return appointmentRepository.save(request);
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + id));
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getWaitingAppointments() {
        return appointmentRepository.findByStatus(AppointmentStatus.WAITING);
    }

    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status);
    }

    public List<Appointment> getQueueForDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorIdAndStatus(doctorId, AppointmentStatus.WAITING);
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public Appointment updateAppointmentStatus(Long id, AppointmentStatus status) {
        Appointment appointment = getAppointmentById(id);
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }
}
