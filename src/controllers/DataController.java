package controllers;
import models.*;
import utils.DataFileHandler;
import java.util.*;
import java.util.stream.Collectors;

/**
 * DataController - Main application controller.
 * @author Utsav Prajapati
 */
public class DataController {
    private Map<String, PatientModel> patientDatabase;
    private Map<String, AppointmentModel> appointmentDatabase;
    private Map<String, PrescriptionModel> prescriptionDatabase;
    private DataFileHandler fileHandler;
    private ReferralController referralController;
    
    public DataController(String dataDirectory) {
        this.patientDatabase = new HashMap<>();
        this.appointmentDatabase = new HashMap<>();
        this.prescriptionDatabase = new HashMap<>();
        this.fileHandler = new DataFileHandler(dataDirectory);
        this.referralController = ReferralController.getInstance();
        initializeData();
    }
    
    private void initializeData() {
        System.out.println("Initializing healthcare data...");
        
        fileHandler.loadPatients().forEach(p -> 
            patientDatabase.put(p.getPatientIdentifier(), p));
        
        fileHandler.loadAppointments().forEach(a -> 
            appointmentDatabase.put(a.getAppointmentIdentifier(), a));
        
        fileHandler.loadPrescriptions().forEach(rx -> 
            prescriptionDatabase.put(rx.getPrescriptionIdentifier(), rx));
        
        fileHandler.loadReferrals().forEach(referralController::addReferral);
        
        System.out.println(String.format("Loaded: %d patients, %d appointments, %d prescriptions, %d referrals",
            patientDatabase.size(), appointmentDatabase.size(), 
            prescriptionDatabase.size(), referralController.getReferralCount()));
    }
    
    public boolean persistAllData() {
        boolean success = true;
        success &= fileHandler.savePatients(new ArrayList<>(patientDatabase.values()));
        success &= fileHandler.saveAppointments(new ArrayList<>(appointmentDatabase.values()));
        success &= fileHandler.savePrescriptions(new ArrayList<>(prescriptionDatabase.values()));
        success &= fileHandler.saveReferrals(new ArrayList<>(referralController.getAllReferrals()));
        return success;
    }
    
    // Patient operations
    public Collection<PatientModel> getAllPatients() {
        return patientDatabase.values();
    }
    
    public PatientModel getPatientByIdentifier(String identifier) {
        return patientDatabase.get(identifier);
    }
    
    public boolean createPatient(PatientModel patient) {
        if (patient != null) {
            patientDatabase.put(patient.getPatientIdentifier(), patient);
            return true;
        }
        return false;
    }
    
    public boolean updatePatient(PatientModel patient) {
        if (patient != null && patientDatabase.containsKey(patient.getPatientIdentifier())) {
            patientDatabase.put(patient.getPatientIdentifier(), patient);
            return true;
        }
        return false;
    }
    
    public boolean deletePatient(String identifier) {
        return patientDatabase.remove(identifier) != null;
    }
    
    // Appointment operations
    public Collection<AppointmentModel> getAllAppointments() {
        return appointmentDatabase.values();
    }
    
    public AppointmentModel getAppointmentByIdentifier(String identifier) {
        return appointmentDatabase.get(identifier);
    }
    
    public boolean createAppointment(AppointmentModel appointment) {
        if (appointment != null) {
            appointmentDatabase.put(appointment.getAppointmentIdentifier(), appointment);
            return true;
        }
        return false;
    }
    
    public boolean updateAppointment(AppointmentModel appointment) {
        if (appointment != null && appointmentDatabase.containsKey(appointment.getAppointmentIdentifier())) {
            appointmentDatabase.put(appointment.getAppointmentIdentifier(), appointment);
            return true;
        }
        return false;
    }
    
    public boolean deleteAppointment(String identifier) {
        return appointmentDatabase.remove(identifier) != null;
    }
    
    // Prescription operations
    public Collection<PrescriptionModel> getAllPrescriptions() {
        return prescriptionDatabase.values();
    }
    
    public PrescriptionModel getPrescriptionByIdentifier(String identifier) {
        return prescriptionDatabase.get(identifier);
    }
    
    public boolean createPrescription(PrescriptionModel prescription) {
        if (prescription != null) {
            prescriptionDatabase.put(prescription.getPrescriptionIdentifier(), prescription);
            generatePrescriptionOutputFile(prescription);
            return true;
        }
        return false;
    }
    
    public boolean updatePrescription(PrescriptionModel prescription) {
        if (prescription != null && prescriptionDatabase.containsKey(prescription.getPrescriptionIdentifier())) {
            prescriptionDatabase.put(prescription.getPrescriptionIdentifier(), prescription);
            return true;
        }
        return false;
    }
    
    public boolean deletePrescription(String identifier) {
        return prescriptionDatabase.remove(identifier) != null;
    }
    
    private void generatePrescriptionOutputFile(PrescriptionModel prescription) {
        try {
            new java.io.File("output").mkdirs();
            String filename = "output/prescription_" + prescription.getPrescriptionIdentifier() + ".txt";
            try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(filename))) {
                writer.println("=".repeat(75));
                writer.println("PRESCRIPTION DOCUMENT");
                writer.println("=".repeat(75));
                writer.println("Prescription ID: " + prescription.getPrescriptionIdentifier());
                writer.println("Patient ID: " + prescription.getPatientIdentifier());
                writer.println("Clinician ID: " + prescription.getClinicianIdentifier());
                writer.println("Date: " + prescription.getPrescriptionDate());
                writer.println("Medication: " + prescription.getMedicationName());
                writer.println("Dosage: " + prescription.getDosageAmount());
                writer.println("Frequency: " + prescription.getAdministrationFrequency());
                writer.println("Duration: " + prescription.getDurationDays() + " days");
                writer.println("Quantity: " + prescription.getTotalQuantity());
                writer.println("Instructions: " + prescription.getPatientInstructions());
                writer.println("Pharmacy: " + prescription.getPharmacyName());
                writer.println("=".repeat(75));
            }
            System.out.println("Generated output: " + filename);
        } catch (java.io.IOException e) {
            System.err.println("Error generating prescription file: " + e.getMessage());
        }
    }
    
    // Referral operations
    public Collection<ReferralModel> getAllReferrals() {
        return referralController.getAllReferrals();
    }
    
    public boolean createReferral(ReferralModel referral) {
        return referralController.addReferral(referral);
    }
    
    public boolean transmitReferral(ReferralModel referral) {
        return referralController.transmitReferral(referral);
    }
    
    // ID generators
    public String generatePatientIdentifier() {
        return "P" + String.format("%03d", patientDatabase.size() + 1);
    }
    
    public String generateAppointmentIdentifier() {
        return "A" + String.format("%03d", appointmentDatabase.size() + 1);
    }
    
    public String generatePrescriptionIdentifier() {
        return "RX" + String.format("%03d", prescriptionDatabase.size() + 1);
    }
}
