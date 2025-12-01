package controllers;
import models.ReferralModel;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.io.*;

/**
 * ReferralController - Double-checked locking Singleton.
 * @author Utsav Prajapati
 */
public class ReferralController {
    private static volatile ReferralController singleInstance = null;
    private Map<String, ReferralModel> referralDatabase;
    private int referralCounter;
    
    private ReferralController() {
        this.referralDatabase = new ConcurrentHashMap<>();
        this.referralCounter = 3000;
        System.out.println("[DOUBLE-CHECKED SINGLETON] ReferralController instantiated");
    }
    
    public static ReferralController getInstance() {
        if (singleInstance == null) {
            synchronized (ReferralController.class) {
                if (singleInstance == null) {
                    singleInstance = new ReferralController();
                }
            }
        }
        return singleInstance;
    }
    
    public boolean addReferral(ReferralModel referral) {
        if (referral != null) {
            referralDatabase.put(referral.getReferralIdentifier(), referral);
            logOperation("Added: " + referral.getReferralIdentifier());
            return true;
        }
        return false;
    }
    
    public Collection<ReferralModel> getAllReferrals() {
        return new ArrayList<>(referralDatabase.values());
    }
    
    public ReferralModel findReferralByIdentifier(String identifier) {
        return referralDatabase.get(identifier);
    }
    
    public String generateReferralIdentifier() {
        return "R" + (++referralCounter);
    }
    
    public boolean transmitReferral(ReferralModel referral) {
        if (referral != null) {
            referral.transmitReferral();
            generateReferralOutputFile(referral);
            logOperation("Transmitted: " + referral.getReferralIdentifier());
            return true;
        }
        return false;
    }
    
    private void generateReferralOutputFile(ReferralModel referral) {
        try {
            new File("output").mkdirs();
            String filename = "output/referral_" + referral.getReferralIdentifier() + ".txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                writer.println("=".repeat(75));
                writer.println("REFERRAL NOTIFICATION DOCUMENT");
                writer.println("=".repeat(75));
                writer.println("Referral ID: " + referral.getReferralIdentifier());
                writer.println("Patient ID: " + referral.getPatientIdentifier());
                writer.println("Referring Clinician: " + referral.getReferringClinicianIdentifier());
                writer.println("Referred To: " + referral.getReferredToClinicianIdentifier());
                writer.println("Date: " + referral.getReferralDate());
                writer.println("Urgency: " + referral.getUrgencyCategory());
                writer.println("Justification: " + referral.getReferralJustification());
                writer.println("Clinical Summary: " + referral.getClinicalSummaryText());
                writer.println("=".repeat(75));
            }
            System.out.println("Generated output: " + filename);
        } catch (IOException e) {
            System.err.println("Error generating referral file: " + e.getMessage());
        }
    }
    
    private void logOperation(String message) {
        System.out.println("[" + java.time.LocalDateTime.now() + "] " + message);
    }
    
    public int getReferralCount() {
        return referralDatabase.size();
    }
}
