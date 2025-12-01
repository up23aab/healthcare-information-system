package views;
import controllers.DataController;
import controllers.ReferralController;
import models.ReferralModel;
import javax.swing.*;
import java.awt.*;

public class ReferralDialog extends JDialog {
    private DataController controller;
    private boolean wasSaved = false;
    
    private JTextField patientIdField;
    private JTextField referringClinicianField;
    private JTextField referredToClinicianField;
    private JTextField referringFacilityField;
    private JTextField referredToFacilityField;
    private JTextField urgencyField;
    private JTextField justificationField;
    private JTextField summaryField;
    
    public ReferralDialog(Frame parent, DataController controller) {
        super(parent, "Add Referral", true);
        this.controller = controller;
        setSize(500, 450);
        setLocationRelativeTo(parent);
        
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        patientIdField = new JTextField();
        referringClinicianField = new JTextField();
        referredToClinicianField = new JTextField();
        referringFacilityField = new JTextField();
        referredToFacilityField = new JTextField();
        urgencyField = new JTextField();
        justificationField = new JTextField();
        summaryField = new JTextField();
        
        formPanel.add(new JLabel("Patient ID:"));
        formPanel.add(patientIdField);
        formPanel.add(new JLabel("Referring Clinician ID:"));
        formPanel.add(referringClinicianField);
        formPanel.add(new JLabel("Referred To Clinician ID:"));
        formPanel.add(referredToClinicianField);
        formPanel.add(new JLabel("Referring Facility ID:"));
        formPanel.add(referringFacilityField);
        formPanel.add(new JLabel("Referred To Facility ID:"));
        formPanel.add(referredToFacilityField);
        formPanel.add(new JLabel("Urgency (Routine/Urgent):"));
        formPanel.add(urgencyField);
        formPanel.add(new JLabel("Justification:"));
        formPanel.add(justificationField);
        formPanel.add(new JLabel("Clinical Summary:"));
        formPanel.add(summaryField);
        
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
            ReferralController referralController = ReferralController.getInstance();
            String id = referralController.generateReferralIdentifier();
            String today = java.time.LocalDate.now().toString();
            
            ReferralModel referral = new ReferralModel();
            referral.setReferralIdentifier(id);
            referral.setPatientIdentifier(patientIdField.getText().trim());
            referral.setReferringClinicianIdentifier(referringClinicianField.getText().trim());
            referral.setReferredToClinicianIdentifier(referredToClinicianField.getText().trim());
            referral.setReferringFacilityIdentifier(referringFacilityField.getText().trim());
            referral.setReferredToFacilityIdentifier(referredToFacilityField.getText().trim());
            referral.setReferralDate(today);
            referral.setUrgencyCategory(urgencyField.getText().trim());
            referral.setReferralJustification(justificationField.getText().trim());
            referral.setClinicalSummaryText(summaryField.getText().trim());
            referral.setRequestedInvestigations("");
            referral.setReferralStatus("New");
            referral.setAssociatedAppointmentIdentifier("");
            referral.setReferralNotes("");
            referral.setCreationDate(today);
            referral.setLastUpdateDate(today);
            
            controller.createReferral(referral);
            
            JOptionPane.showMessageDialog(this, 
                "Referral created successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            wasSaved = true;
            dispose();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error creating referral: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    public boolean isSaved() {
        return wasSaved;
    }
}
