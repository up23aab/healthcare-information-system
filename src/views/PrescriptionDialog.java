package views;
import controllers.DataController;
import models.PrescriptionModel;
import javax.swing.*;
import java.awt.*;

public class PrescriptionDialog extends JDialog {
    private DataController controller;
    private PrescriptionModel prescription;
    private boolean wasSaved = false;
    
    private JTextField idField;
    private JTextField patientIdField;
    private JTextField clinicianIdField;
    private JTextField medicationField;
    private JTextField dosageField;
    private JTextField frequencyField;
    private JTextField durationField;
    private JTextField quantityField;
    private JTextField instructionsField;
    private JTextField pharmacyField;
    
    public PrescriptionDialog(Frame parent, PrescriptionModel prescription, DataController controller) {
        super(parent, prescription == null ? "Add Prescription" : "Edit Prescription", true);
        this.controller = controller;
        this.prescription = prescription;
        setSize(500, 500);
        setLocationRelativeTo(parent);
        
        JPanel formPanel = new JPanel(new GridLayout(10, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        idField = new JTextField();
        patientIdField = new JTextField();
        clinicianIdField = new JTextField();
        medicationField = new JTextField();
        dosageField = new JTextField();
        frequencyField = new JTextField();
        durationField = new JTextField();
        quantityField = new JTextField();
        instructionsField = new JTextField();
        pharmacyField = new JTextField();
        
        formPanel.add(new JLabel("Prescription ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Patient ID:"));
        formPanel.add(patientIdField);
        formPanel.add(new JLabel("Clinician ID:"));
        formPanel.add(clinicianIdField);
        formPanel.add(new JLabel("Medication Name:"));
        formPanel.add(medicationField);
        formPanel.add(new JLabel("Dosage Amount:"));
        formPanel.add(dosageField);
        formPanel.add(new JLabel("Frequency:"));
        formPanel.add(frequencyField);
        formPanel.add(new JLabel("Duration (days):"));
        formPanel.add(durationField);
        formPanel.add(new JLabel("Total Quantity:"));
        formPanel.add(quantityField);
        formPanel.add(new JLabel("Instructions:"));
        formPanel.add(instructionsField);
        formPanel.add(new JLabel("Pharmacy Name:"));
        formPanel.add(pharmacyField);
        
        if (prescription != null) {
            idField.setText(prescription.getPrescriptionIdentifier());
            idField.setEditable(false);
            patientIdField.setText(prescription.getPatientIdentifier());
            clinicianIdField.setText(prescription.getClinicianIdentifier());
            medicationField.setText(prescription.getMedicationName());
            dosageField.setText(prescription.getDosageAmount());
            frequencyField.setText(prescription.getAdministrationFrequency());
            durationField.setText(String.valueOf(prescription.getDurationDays()));
            quantityField.setText(String.valueOf(prescription.getTotalQuantity()));
            instructionsField.setText(prescription.getPatientInstructions());
            pharmacyField.setText(prescription.getPharmacyName());
        }
        
        JPanel buttonPanel = new JPanel();
        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> save());
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dispose());
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void save() {
        try {
            String id = idField.getText().trim();
            if (id.isEmpty()) {
                id = controller.generatePrescriptionIdentifier();
            }
            
            int duration = 7;
            int quantity = 10;
            try {
                duration = Integer.parseInt(durationField.getText().trim());
                quantity = Integer.parseInt(quantityField.getText().trim());
            } catch (NumberFormatException e) {
                // Use defaults
            }
            
            String today = java.time.LocalDate.now().toString();
            
            if (prescription == null) {
                // Creating new prescription
                prescription = new PrescriptionModel(
                    id,
                    patientIdField.getText().trim(),
                    clinicianIdField.getText().trim(),
                    "",
                    today,
                    medicationField.getText().trim(),
                    dosageField.getText().trim(),
                    frequencyField.getText().trim(),
                    duration,
                    quantity,
                    instructionsField.getText().trim(),
                    pharmacyField.getText().trim(),
                    "Issued",
                    today,
                    ""
                );
                controller.createPrescription(prescription);
                JOptionPane.showMessageDialog(this, "Prescription created successfully!");
            } else {
                // Editing existing prescription
                prescription.setPatientIdentifier(patientIdField.getText().trim());
                prescription.setClinicianIdentifier(clinicianIdField.getText().trim());
                prescription.setMedicationName(medicationField.getText().trim());
                prescription.setDosageAmount(dosageField.getText().trim());
                prescription.setAdministrationFrequency(frequencyField.getText().trim());
                prescription.setDurationDays(duration);
                prescription.setTotalQuantity(quantity);
                prescription.setPatientInstructions(instructionsField.getText().trim());
                prescription.setPharmacyName(pharmacyField.getText().trim());
                
                controller.updatePrescription(prescription);
                JOptionPane.showMessageDialog(this, "Prescription updated successfully!");
            }
            
            wasSaved = true;
            dispose();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error saving prescription: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    public boolean isSaved() {
        return wasSaved;
    }
}
