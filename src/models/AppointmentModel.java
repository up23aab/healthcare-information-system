package models;

import java.time.LocalDateTime;

/**
 * Appointment model class.
 * @author Utsav Prajapati
 */
public class AppointmentModel {
    private String appointmentIdentifier;
    private String patientIdentifier;
    private String clinicianIdentifier;
    private String facilityIdentifier;
    private String appointmentDate;
    private String appointmentTime;
    private int durationInMinutes;
    private String appointmentCategory;
    private String currentStatus;
    private String visitPurpose;
    private String additionalNotes;
    private String creationTimestamp;
    private String lastModificationTimestamp;
    
    public AppointmentModel() {
        this.currentStatus = "Scheduled";
    }
    
    public AppointmentModel(String appointmentIdentifier, String patientIdentifier,
                           String clinicianIdentifier, String facilityIdentifier,
                           String appointmentDate, String appointmentTime,
                           int durationInMinutes, String appointmentCategory,
                           String currentStatus, String visitPurpose,
                           String additionalNotes, String creationTimestamp,
                           String lastModificationTimestamp) {
        this.appointmentIdentifier = appointmentIdentifier;
        this.patientIdentifier = patientIdentifier;
        this.clinicianIdentifier = clinicianIdentifier;
        this.facilityIdentifier = facilityIdentifier;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.durationInMinutes = durationInMinutes;
        this.appointmentCategory = appointmentCategory;
        this.currentStatus = currentStatus;
        this.visitPurpose = visitPurpose;
        this.additionalNotes = additionalNotes;
        this.creationTimestamp = creationTimestamp;
        this.lastModificationTimestamp = lastModificationTimestamp;
    }
    
    // Getters
    public String getAppointmentIdentifier() { return appointmentIdentifier; }
    public String getPatientIdentifier() { return patientIdentifier; }
    public String getClinicianIdentifier() { return clinicianIdentifier; }
    public String getFacilityIdentifier() { return facilityIdentifier; }
    public String getAppointmentDate() { return appointmentDate; }
    public String getAppointmentTime() { return appointmentTime; }
    public int getDurationInMinutes() { return durationInMinutes; }
    public String getAppointmentCategory() { return appointmentCategory; }
    public String getCurrentStatus() { return currentStatus; }
    public String getVisitPurpose() { return visitPurpose; }
    public String getAdditionalNotes() { return additionalNotes; }
    public String getCreationTimestamp() { return creationTimestamp; }
    public String getLastModificationTimestamp() { return lastModificationTimestamp; }
    
    // Setters
    public void setAppointmentIdentifier(String id) { this.appointmentIdentifier = id; }
    public void setPatientIdentifier(String id) { this.patientIdentifier = id; }
    public void setClinicianIdentifier(String id) { this.clinicianIdentifier = id; }
    public void setFacilityIdentifier(String id) { this.facilityIdentifier = id; }
    public void setAppointmentDate(String date) { this.appointmentDate = date; }
    public void setAppointmentTime(String time) { this.appointmentTime = time; }
    public void setDurationInMinutes(int duration) { this.durationInMinutes = duration; }
    public void setAppointmentCategory(String category) { this.appointmentCategory = category; }
    public void setCurrentStatus(String status) { this.currentStatus = status; }
    public void setVisitPurpose(String purpose) { this.visitPurpose = purpose; }
    public void setAdditionalNotes(String notes) { this.additionalNotes = notes; }
    public void setCreationTimestamp(String timestamp) { this.creationTimestamp = timestamp; }
    public void setLastModificationTimestamp(String timestamp) { this.lastModificationTimestamp = timestamp; }
    
    public boolean confirmAppointment() {
        this.currentStatus = "Confirmed";
        this.lastModificationTimestamp = LocalDateTime.now().toString();
        return true;
    }
    
    public boolean cancelAppointment() {
        this.currentStatus = "Cancelled";
        this.lastModificationTimestamp = LocalDateTime.now().toString();
        return true;
    }
    
    @Override
    public String toString() {
        return String.format("Appointment[ID=%s, Patient=%s, Date=%s %s, Status=%s]",
            appointmentIdentifier, patientIdentifier, appointmentDate, 
            appointmentTime, currentStatus);
    }
    
    public String toCSVFormat() {
        return String.join(",",
            appointmentIdentifier, patientIdentifier, clinicianIdentifier,
            facilityIdentifier, appointmentDate, appointmentTime,
            String.valueOf(durationInMinutes), appointmentCategory, currentStatus,
            visitPurpose, "\"" + additionalNotes + "\"",
            creationTimestamp, lastModificationTimestamp
        );
    }
}
