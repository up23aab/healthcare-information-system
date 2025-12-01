package utils;
import models.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import java.io.*;

public class DataFileHandler {
    private String dataPath;
    
    public DataFileHandler(String path) { this.dataPath = path; }
    
    public List<PatientModel> loadPatients() {
        try {
            return Files.lines(Paths.get(dataPath, "patients.csv"))
                .skip(1)
                .map(this::parsePatient)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error loading patients: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    private PatientModel parsePatient(String line) {
        String[] d = parseLine(line);
        if (d.length >= 14) {
            return new PatientModel(d[0], d[1], d[2], d[3], d[4], d[5], d[6], d[7], d[8], d[9], d[10], d[11], d[12], d[13]);
        }
        return null;
    }
    
    public List<AppointmentModel> loadAppointments() {
        try {
            return Files.lines(Paths.get(dataPath, "appointments.csv"))
                .skip(1)
                .map(this::parseAppointment)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error loading appointments: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    private AppointmentModel parseAppointment(String line) {
        String[] d = parseLine(line);
        if (d.length >= 13) {
            int dur = 0;
            try { dur = Integer.parseInt(d[6]); } catch (Exception e) {}
            return new AppointmentModel(d[0], d[1], d[2], d[3], d[4], d[5], dur, d[7], d[8], d[9], d[10], d[11], d[12]);
        }
        return null;
    }
    
    public List<PrescriptionModel> loadPrescriptions() {
        try {
            return Files.lines(Paths.get(dataPath, "prescriptions.csv"))
                .skip(1)
                .map(this::parsePrescription)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error loading prescriptions: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    private PrescriptionModel parsePrescription(String line) {
        String[] d = parseLine(line);
        if (d.length >= 15) {
            int dur = 0, qty = 0;
            try {
                dur = Integer.parseInt(d[8]);
                qty = Integer.parseInt(d[9]);
            } catch (Exception e) {}
            return new PrescriptionModel(d[0], d[1], d[2], d[3], d[4], d[5], d[6], d[7], dur, qty, d[10], d[11], d[12], d[13], d[14]);
        }
        return null;
    }
    
    public List<ReferralModel> loadReferrals() {
        try {
            return Files.lines(Paths.get(dataPath, "referrals.csv"))
                .skip(1)
                .map(this::parseReferral)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error loading referrals: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    private ReferralModel parseReferral(String line) {
        String[] d = parseLine(line);
        if (d.length >= 16) {
            ReferralModel r = new ReferralModel();
            r.setReferralIdentifier(d[0]);
            r.setPatientIdentifier(d[1]);
            r.setReferringClinicianIdentifier(d[2]);
            r.setReferredToClinicianIdentifier(d[3]);
            r.setReferringFacilityIdentifier(d[4]);
            r.setReferredToFacilityIdentifier(d[5]);
            r.setReferralDate(d[6]);
            r.setUrgencyCategory(d[7]);
            r.setReferralJustification(d[8]);
            r.setClinicalSummaryText(d[9]);
            r.setRequestedInvestigations(d[10]);
            r.setReferralStatus(d[11]);
            r.setAssociatedAppointmentIdentifier(d[12]);
            r.setReferralNotes(d[13]);
            r.setCreationDate(d[14]);
            r.setLastUpdateDate(d[15]);
            return r;
        }
        return null;
    }
    
    public boolean savePatients(List<PatientModel> patients) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(Paths.get(dataPath, "patients.csv").toString()))) {
            pw.println("patient_id,first_name,last_name,date_of_birth,nhs_number,gender,phone_number,email,address,postcode,emergency_contact_name,emergency_contact_phone,registration_date,gp_surgery_id");
            patients.forEach(p -> pw.println(p.toCSVFormat()));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public boolean saveAppointments(List<AppointmentModel> appointments) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(Paths.get(dataPath, "appointments.csv").toString()))) {
            pw.println("appointment_id,patient_id,clinician_id,facility_id,appointment_date,appointment_time,duration_minutes,appointment_type,status,reason_for_visit,notes,created_date,last_modified");
            appointments.forEach(a -> pw.println(a.toCSVFormat()));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public boolean savePrescriptions(List<PrescriptionModel> prescriptions) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(Paths.get(dataPath, "prescriptions.csv").toString()))) {
            pw.println("prescription_id,patient_id,clinician_id,appointment_id,prescription_date,medication_name,dosage,frequency,duration_days,quantity,instructions,pharmacy_name,status,issue_date,collection_date");
            prescriptions.forEach(p -> pw.println(p.toCSVFormat()));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public boolean saveReferrals(List<ReferralModel> referrals) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(Paths.get(dataPath, "referrals.csv").toString()))) {
            pw.println("referral_id,patient_id,referring_clinician_id,referred_to_clinician_id,referring_facility_id,referred_to_facility_id,referral_date,urgency_level,referral_reason,clinical_summary,requested_investigations,status,appointment_id,notes,created_date,last_updated");
            referrals.forEach(r -> pw.println(r.toCSVFormat()));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    private String[] parseLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuote = false;
        StringBuilder current = new StringBuilder();
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuote = !inQuote;
            } else if (c == ',' && !inQuote) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());
        return result.toArray(new String[0]);
    }
}
