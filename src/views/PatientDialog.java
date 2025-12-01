package views;
import controllers.DataController;
import models.PatientModel;
import javax.swing.*;
import java.awt.*;

public class PatientDialog extends JDialog {
    private DataController controller;
    private PatientModel patient;
    private boolean wasSaved = false;
    
    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField birthDateField;
    private JTextField nhsNumberField;
    private JTextField genderField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField postcodeField;
    private JTextField emergNameField;
    private JTextField emergPhoneField;
    
    public PatientDialog(Frame parent, PatientModel patient, DataController controller) {
        super(parent, patient == null ? "Add Patient" : "Edit Patient", true);
        this.controller = controller;
        this.patient = patient;
        setSize(500, 600);
        setLocationRelativeTo(parent);
        
        JPanel formPanel = new JPanel(new GridLayout(13, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        idField = new JTextField();
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        birthDateField = new JTextField();
        nhsNumberField = new JTextField();
        genderField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        addressField = new JTextField();
        postcodeField = new JTextField();
        emergNameField = new JTextField();
        emergPhoneField = new JTextField();
        
        formPanel.add(new JLabel("Patient ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Birth Date (YYYY-MM-DD):"));
        formPanel.add(birthDateField);
        formPanel.add(new JLabel("NHS Number:"));
        formPanel.add(nhsNumberField);
        formPanel.add(new JLabel("Gender (M/F):"));
        formPanel.add(genderField);
        formPanel.add(new JLabel("Phone Number:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Email Address:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Residential Address:"));
        formPanel.add(addressField);
        formPanel.add(new JLabel("Postal Code:"));
        formPanel.add(postcodeField);
        formPanel.add(new JLabel("Emergency Contact Name:"));
        formPanel.add(emergNameField);
        formPanel.add(new JLabel("Emergency Contact Phone:"));
        formPanel.add(emergPhoneField);
        
        if (patient != null) {
            idField.setText(patient.getPatientIdentifier());
            idField.setEditable(false);
            firstNameField.setText(patient.getFirstName());
            lastNameField.setText(patient.getLastName());
            birthDateField.setText(patient.getBirthDate());
            nhsNumberField.setText(patient.getNationalHealthNumber());
            genderField.setText(patient.getGender());
            phoneField.setText(patient.getPhoneNumber());
            emailField.setText(patient.getEmailAddress());
            addressField.setText(patient.getResidentialAddress());
            postcodeField.setText(patient.getPostalCode());
            emergNameField.setText(patient.getEmergencyContactName());
            emergPhoneField.setText(patient.getEmergencyContactPhone());
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
                id = controller.generatePatientIdentifier();
            }
            
            String today = java.time.LocalDate.now().toString();
            
            if (patient == null) {
                // Creating new patient
                patient = new PatientModel(
                    id,
                    firstNameField.getText().trim(),
                    lastNameField.getText().trim(),
                    birthDateField.getText().trim(),
                    nhsNumberField.getText().trim(),
                    genderField.getText().trim(),
                    phoneField.getText().trim(),
                    emailField.getText().trim(),
                    addressField.getText().trim(),
                    postcodeField.getText().trim(),
                    emergNameField.getText().trim(),
                    emergPhoneField.getText().trim(),
                    today,
                    "S001"
                );
                controller.createPatient(patient);
                JOptionPane.showMessageDialog(this, "Patient created successfully!");
            } else {
                // Editing existing patient
                patient.setFirstName(firstNameField.getText().trim());
                patient.setLastName(lastNameField.getText().trim());
                patient.setBirthDate(birthDateField.getText().trim());
                patient.setNationalHealthNumber(nhsNumberField.getText().trim());
                patient.setGender(genderField.getText().trim());
                patient.setPhoneNumber(phoneField.getText().trim());
                patient.setEmailAddress(emailField.getText().trim());
                patient.setResidentialAddress(addressField.getText().trim());
                patient.setPostalCode(postcodeField.getText().trim());
                patient.setEmergencyContactName(emergNameField.getText().trim());
                patient.setEmergencyContactPhone(emergPhoneField.getText().trim());
                
                controller.updatePatient(patient);
                JOptionPane.showMessageDialog(this, "Patient updated successfully!");
            }
            
            wasSaved = true;
            dispose();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error saving patient: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    public boolean isSaved() {
        return wasSaved;
    }
}
