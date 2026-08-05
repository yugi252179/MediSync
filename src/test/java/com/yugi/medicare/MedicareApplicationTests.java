package com.yugi.medicare;

import com.yugi.medicare.dto.PrescriptionRequest;
import com.yugi.medicare.entity.*;
import com.yugi.medicare.service.AppointmentService;
import com.yugi.medicare.service.DoctorService;
import com.yugi.medicare.service.PatientService;
import com.yugi.medicare.service.PrescriptionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MedicareApplicationTests {

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private PrescriptionService prescriptionService;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(doctorService);
		Assertions.assertNotNull(patientService);
		Assertions.assertNotNull(appointmentService);
		Assertions.assertNotNull(prescriptionService);
	}

	@Test
	void testFullMedicareWorkflow() {
		// 1. Create Doctor
		Doctor doctor = new Doctor();
		doctor.setName("Dr. Sarah Smith");
		doctor.setSpecialization("Cardiology");
		Doctor savedDoctor = doctorService.saveDoctor(doctor);
		Assertions.assertNotNull(savedDoctor.getId());

		// 2. Book Appointment for Patient
		Patient patient = new Patient();
		patient.setName("John Doe");
		patient.setMobile("9876543210");
		patient.setAge(35);
		patient.setGender("Male");

		Appointment appointment = new Appointment();
		appointment.setDoctor(savedDoctor);
		appointment.setPatient(patient);
		appointment.setReason("Chest Pain and Dizziness");

		Appointment bookedAppointment = appointmentService.createAppointment(appointment);
		Assertions.assertNotNull(bookedAppointment.getId());
		Assertions.assertEquals(AppointmentStatus.WAITING, bookedAppointment.getStatus());

		// 3. Verify Patient Queue
		List<Appointment> queue = appointmentService.getQueueForDoctor(savedDoctor.getId());
		Assertions.assertFalse(queue.isEmpty());
		Assertions.assertEquals(bookedAppointment.getId(), queue.get(0).getId());

		// 4. Issue Prescription by Doctor
		PrescriptionRequest request = new PrescriptionRequest();
		request.setAppointmentId(bookedAppointment.getId());
		request.setDiagnosis("Mild Hypertension");
		request.setMedicines("Aspirin 75mg once daily, Lisinopril 10mg once daily");

		Prescription prescription = prescriptionService.createPrescription(request);
		Assertions.assertNotNull(prescription.getId());
		Assertions.assertEquals("Mild Hypertension", prescription.getDiagnosis());

		// 5. Verify Appointment Status changed to COMPLETED
		Appointment completedAppt = appointmentService.getAppointmentById(bookedAppointment.getId());
		Assertions.assertEquals(AppointmentStatus.COMPLETED, completedAppt.getStatus());

		// 6. Verify Patient Prescription History
		List<Prescription> patientPrescriptions = prescriptionService.getPrescriptionsByPatient(bookedAppointment.getPatient().getId());
		Assertions.assertEquals(1, patientPrescriptions.size());
	}
}
