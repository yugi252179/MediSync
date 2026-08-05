package com.yugi.medicare.repository;

import com.yugi.medicare.entity.Appointment;
import com.yugi.medicare.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByStatus(AppointmentStatus status);
    List<Appointment> findByDoctorIdAndStatus(Long doctorId, AppointmentStatus status);
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDoctorId(Long doctorId);
}
