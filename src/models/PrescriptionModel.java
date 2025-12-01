package models;

public class PrescriptionModel {
    private String prescriptionIdentifier;
    private String patientIdentifier;
    private String clinicianIdentifier;
    private String appointmentIdentifier;
    private String prescriptionDate;
    private String medicationName;
    private String dosageAmount;
    private String administrationFrequency;
    private int durationDays;
    private int totalQuantity;
    private String patientInstructions;
    private String pharmacyName;
    private String prescriptionStatus;
    private String issueDate;
    private String collectionDate;
    
    public PrescriptionModel() {
        this.prescriptionStatus = "Issued";
    }
    
    public PrescriptionModel(String prescriptionIdentifier, String patientIdentifier,
                            String clinicianIdentifier, String appointmentIdentifier,
                            String prescriptionDate, String medicationName,
                            String dosageAmount, String administrationFrequency,
                            int durationDays, int totalQuantity, String patientInstructions,
                            String pharmacyName, String prescriptionStatus,
                            String issueDate, String collectionDate) {
        this.prescriptionIdentifier = prescriptionIdentifier;
        this.patientIdentifier = patientIdentifier;
        this.clinicianIdentifier = clinicianIdentifier;
        this.appointmentIdentifier = appointmentIdentifier;
        this.prescriptionDate = prescriptionDate;
        this.medicationName = medicationName;
        this.dosageAmount = dosageAmount;
        this.administrationFrequency = administrationFrequency;
        this.durationDays = durationDays;
        this.totalQuantity = totalQuantity;
        this.patientInstructions = patientInstructions;
        this.pharmacyName = pharmacyName;
        this.prescriptionStatus = prescriptionStatus;
        this.issueDate = issueDate;
        this.collectionDate = collectionDate;
    }
    
    // Getters
    public String getPrescriptionIdentifier() { return prescriptionIdentifier; }
    public String getPatientIdentifier() { return patientIdentifier; }
    public String getClinicianIdentifier() { return clinicianIdentifier; }
    public String getAppointmentIdentifier() { return appointmentIdentifier; }
    public String getPrescriptionDate() { return prescriptionDate; }
    public String getMedicationName() { return medicationName; }
    public String getDosageAmount() { return dosageAmount; }
    public String getAdministrationFrequency() { return administrationFrequency; }
    public int getDurationDays() { return durationDays; }
    public int getTotalQuantity() { return totalQuantity; }
    public String getPatientInstructions() { return patientInstructions; }
    public String getPharmacyName() { return pharmacyName; }
    public String getPrescriptionStatus() { return prescriptionStatus; }
    public String getIssueDate() { return issueDate; }
    public String getCollectionDate() { return collectionDate; }
    
    // Setters
    public void setPrescriptionIdentifier(String id) { this.prescriptionIdentifier = id; }
    public void setPatientIdentifier(String id) { this.patientIdentifier = id; }
    public void setClinicianIdentifier(String id) { this.clinicianIdentifier = id; }
    public void setAppointmentIdentifier(String id) { this.appointmentIdentifier = id; }
    public void setPrescriptionDate(String date) { this.prescriptionDate = date; }
    public void setMedicationName(String name) { this.medicationName = name; }
    public void setDosageAmount(String amount) { this.dosageAmount = amount; }
    public void setAdministrationFrequency(String frequency) { this.administrationFrequency = frequency; }
    public void setDurationDays(int days) { this.durationDays = days; }
    public void setTotalQuantity(int quantity) { this.totalQuantity = quantity; }
    public void setPatientInstructions(String instructions) { this.patientInstructions = instructions; }
    public void setPharmacyName(String name) { this.pharmacyName = name; }
    public void setPrescriptionStatus(String status) { this.prescriptionStatus = status; }
    public void setIssueDate(String date) { this.issueDate = date; }
    public void setCollectionDate(String date) { this.collectionDate = date; }
    
    @Override
    public String toString() {
        return String.format("Prescription[ID=%s, Medication=%s, Patient=%s]",
            prescriptionIdentifier, medicationName, patientIdentifier);
    }
    
    public String toCSVFormat() {
        return String.join(",",
            prescriptionIdentifier, patientIdentifier, clinicianIdentifier,
            appointmentIdentifier, prescriptionDate, medicationName, dosageAmount,
            administrationFrequency, String.valueOf(durationDays),
            String.valueOf(totalQuantity), patientInstructions, pharmacyName,
            prescriptionStatus, issueDate, collectionDate
        );
    }
}
