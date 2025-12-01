package models;

public class ReferralModel {
    private String referralIdentifier;
    private String patientIdentifier;
    private String referringClinicianIdentifier;
    private String referredToClinicianIdentifier;
    private String referringFacilityIdentifier;
    private String referredToFacilityIdentifier;
    private String referralDate;
    private String urgencyCategory;
    private String referralJustification;
    private String clinicalSummaryText;
    private String requestedInvestigations;
    private String referralStatus;
    private String associatedAppointmentIdentifier;
    private String referralNotes;
    private String creationDate;
    private String lastUpdateDate;
    
    public ReferralModel() {
        this.referralStatus = "New";
        this.urgencyCategory = "Routine";
        this.clinicalSummaryText = "";
        this.referralNotes = "";
    }
    
    // Getters
    public String getReferralIdentifier() { return referralIdentifier; }
    public String getPatientIdentifier() { return patientIdentifier; }
    public String getReferringClinicianIdentifier() { return referringClinicianIdentifier; }
    public String getReferredToClinicianIdentifier() { return referredToClinicianIdentifier; }
    public String getReferringFacilityIdentifier() { return referringFacilityIdentifier; }
    public String getReferredToFacilityIdentifier() { return referredToFacilityIdentifier; }
    public String getReferralDate() { return referralDate; }
    public String getUrgencyCategory() { return urgencyCategory; }
    public String getReferralJustification() { return referralJustification; }
    public String getClinicalSummaryText() { return clinicalSummaryText; }
    public String getRequestedInvestigations() { return requestedInvestigations; }
    public String getReferralStatus() { return referralStatus; }
    public String getAssociatedAppointmentIdentifier() { return associatedAppointmentIdentifier; }
    public String getReferralNotes() { return referralNotes; }
    public String getCreationDate() { return creationDate; }
    public String getLastUpdateDate() { return lastUpdateDate; }
    
    // Setters
    public void setReferralIdentifier(String id) { this.referralIdentifier = id; }
    public void setPatientIdentifier(String id) { this.patientIdentifier = id; }
    public void setReferringClinicianIdentifier(String id) { this.referringClinicianIdentifier = id; }
    public void setReferredToClinicianIdentifier(String id) { this.referredToClinicianIdentifier = id; }
    public void setReferringFacilityIdentifier(String id) { this.referringFacilityIdentifier = id; }
    public void setReferredToFacilityIdentifier(String id) { this.referredToFacilityIdentifier = id; }
    public void setReferralDate(String date) { this.referralDate = date; }
    public void setUrgencyCategory(String category) { this.urgencyCategory = category; }
    public void setReferralJustification(String justification) { this.referralJustification = justification; }
    public void setClinicalSummaryText(String summary) { this.clinicalSummaryText = summary; }
    public void setRequestedInvestigations(String investigations) { this.requestedInvestigations = investigations; }
    public void setReferralStatus(String status) { this.referralStatus = status; }
    public void setAssociatedAppointmentIdentifier(String id) { this.associatedAppointmentIdentifier = id; }
    public void setReferralNotes(String notes) { this.referralNotes = notes; }
    public void setCreationDate(String date) { this.creationDate = date; }
    public void setLastUpdateDate(String date) { this.lastUpdateDate = date; }
    
    public void transmitReferral() {
        this.referralStatus = "Transmitted";
        this.lastUpdateDate = java.time.LocalDateTime.now().toString();
    }
    
    @Override
    public String toString() {
        return String.format("Referral[ID=%s, Patient=%s, Urgency=%s, Status=%s]",
            referralIdentifier, patientIdentifier, urgencyCategory, referralStatus);
    }
    
    public String toCSVFormat() {
        return String.join(",",
            referralIdentifier != null ? referralIdentifier : "",
            patientIdentifier != null ? patientIdentifier : "",
            referringClinicianIdentifier != null ? referringClinicianIdentifier : "",
            referredToClinicianIdentifier != null ? referredToClinicianIdentifier : "",
            referringFacilityIdentifier != null ? referringFacilityIdentifier : "",
            referredToFacilityIdentifier != null ? referredToFacilityIdentifier : "",
            referralDate != null ? referralDate : "",
            urgencyCategory != null ? urgencyCategory : "",
            referralJustification != null ? referralJustification : "",
            "\"" + (clinicalSummaryText != null ? clinicalSummaryText : "") + "\"",
            requestedInvestigations != null ? requestedInvestigations : "",
            referralStatus != null ? referralStatus : "",
            associatedAppointmentIdentifier != null ? associatedAppointmentIdentifier : "",
            "\"" + (referralNotes != null ? referralNotes : "") + "\"",
            creationDate != null ? creationDate : "",
            lastUpdateDate != null ? lastUpdateDate : ""
        );
    }
}
