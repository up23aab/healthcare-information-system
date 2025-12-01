package models;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Patient model class.
 * @author Utsav Prajapati
 */
public class PatientModel {
    private String patientIdentifier;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String nationalHealthNumber;
    private String gender;
    private String phoneNumber;
    private String emailAddress;
    private String residentialAddress;
    private String postalCode;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String registrationDate;
    private String gpSurgeryIdentifier;
    
    private Map<String, AppointmentModel> appointmentMap;
    private List<String> medicalHistory;
    
    public PatientModel() {
        this.appointmentMap = new HashMap<>();
        this.medicalHistory = new ArrayList<>();
    }
    
    public PatientModel(String patientIdentifier, String firstName, String lastName,
                       String birthDate, String nationalHealthNumber, String gender,
                       String phoneNumber, String emailAddress, String residentialAddress,
                       String postalCode, String emergencyContactName,
                       String emergencyContactPhone, String registrationDate,
                       String gpSurgeryIdentifier) {
        this();
        this.patientIdentifier = patientIdentifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.nationalHealthNumber = nationalHealthNumber;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.residentialAddress = residentialAddress;
        this.postalCode = postalCode;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.registrationDate = registrationDate;
        this.gpSurgeryIdentifier = gpSurgeryIdentifier;
    }
    
    // Getters
    public String getPatientIdentifier() { return patientIdentifier; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getBirthDate() { return birthDate; }
    public String getNationalHealthNumber() { return nationalHealthNumber; }
    public String getGender() { return gender; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmailAddress() { return emailAddress; }
    public String getResidentialAddress() { return residentialAddress; }
    public String getPostalCode() { return postalCode; }
    public String getEmergencyContactName() { return emergencyContactName; }
    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public String getRegistrationDate() { return registrationDate; }
    public String getGpSurgeryIdentifier() { return gpSurgeryIdentifier; }
    
    // Setters
    public void setPatientIdentifier(String id) { this.patientIdentifier = id; }
    public void setFirstName(String name) { this.firstName = name; }
    public void setLastName(String name) { this.lastName = name; }
    public void setBirthDate(String date) { this.birthDate = date; }
    public void setNationalHealthNumber(String number) { this.nationalHealthNumber = number; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPhoneNumber(String phone) { this.phoneNumber = phone; }
    public void setEmailAddress(String email) { this.emailAddress = email; }
    public void setResidentialAddress(String address) { this.residentialAddress = address; }
    public void setPostalCode(String code) { this.postalCode = code; }
    public void setEmergencyContactName(String name) { this.emergencyContactName = name; }
    public void setEmergencyContactPhone(String phone) { this.emergencyContactPhone = phone; }
    public void setRegistrationDate(String date) { this.registrationDate = date; }
    public void setGpSurgeryIdentifier(String id) { this.gpSurgeryIdentifier = id; }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public boolean registerAppointment(AppointmentModel appointment) {
        if (appointment != null) {
            appointmentMap.put(appointment.getAppointmentIdentifier(), appointment);
            return true;
        }
        return false;
    }
    
    public List<AppointmentModel> getAllAppointments() {
        return new ArrayList<>(appointmentMap.values());
    }
    
    public void addMedicalHistory(String entry) {
        medicalHistory.add(entry);
    }
    
    @Override
    public String toString() {
        return String.format("Patient[ID=%s, Name=%s, NHS=%s, DOB=%s]",
            patientIdentifier, getFullName(), nationalHealthNumber, birthDate);
    }
    
    public String toCSVFormat() {
        return String.join(",",
            patientIdentifier, firstName, lastName, birthDate, nationalHealthNumber,
            gender, phoneNumber, emailAddress, 
            "\"" + residentialAddress + "\"", postalCode,
            emergencyContactName, emergencyContactPhone, 
            registrationDate, gpSurgeryIdentifier
        );
    }
}
