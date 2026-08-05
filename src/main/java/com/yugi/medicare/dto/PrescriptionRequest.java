package com.yugi.medicare.dto;

public class PrescriptionRequest {
    private Long appointmentId;
    private String diagnosis;
    private String medicines;

    public PrescriptionRequest() {
    }

    public PrescriptionRequest(Long appointmentId, String diagnosis, String medicines) {
        this.appointmentId = appointmentId;
        this.diagnosis = diagnosis;
        this.medicines = medicines;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }
}
