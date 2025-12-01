package views;
import controllers.DataController;
import models.AppointmentModel;
import javax.swing.*;
import java.awt.*;

public class AppointmentDialog extends JDialog {
    private DataController controller;
    private AppointmentModel appointment;
    private boolean wasSaved = false;
    
    private JTextField idField;
    private JTextField patientIdField;
    private JTextField clinicianIdField;
    private JTextField facilityIdField;
    private JTextField dateField;
    private JTextField timeField;
    private JTextField durationField;
    private JTextField typeField;
    private JTextField statusField;
    private JTextField reasonField;
    
    public AppointmentDialog(Frame parent, AppointmentModel appointment, DataController controller) {
        super(parent, appointment == null ? "Add Appointment" : "Edit Appointment", true);
        this.controller = controller;
        this.appointment = appointment;
        setSize(500, 500);
        setLocationRelativeTo(parent);
        
        JPanel formPanel = new JPanel(new GridLayout(10, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        idField = new JTextField();
        patientIdField = new JTextField();
        clinicianIdField = new JTextField();
        facilityIdField = new JTextField();
        dateField = new JTextField();
        timeField = new JTextField();
        durationField = new JTextField();
        typeField = new JTextField();
        statusField = new JTextField();
        reasonField = new JTextField();
        
        formPanel.add(new JLabel("Appointment ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Patient ID:"));
        formPanel.add(patientIdField);
        formPanel.add(new JLabel("Clinician ID:"));
        formPanel.add(clinicianIdField);
        formPanel.add(new JLabel("Facility ID:"));
        formPanel.add(facilityIdField);
        formPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        formPanel.add(dateField);
        formPanel.add(new JLabel("Time (HH:MM):"));
        formPanel.add(timeField);
        formPanel.add(new JLabel("Duration (minutes):"));
        formPanel.add(durationField);
        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeField);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(statusField);
        formPanel.add(new JLabel("Reason for Visit:"));
        formPanel.add(reasonField);
        
        if (appointment != null) {
            idField.setText(appointment.getAppointmentIdentifier());
            idField.setEditable(false);
            patientIdField.setText(appointment.getPatientIdentifier());
            clinicianIdField.setText(appointment.getClinicianIdentifier());
            facilityIdField.setText(appointment.getFacilityIdentifier());
            dateField.setText(appointment.getAppointmentDate());
            timeField.setText(appointment.getAppointmentTime());
            durationField.setText(String.valueOf(appointment.getDurationInMinutes()));
            typeField.setText(appointment.getAppointmentCategory());
            statusField.setText(appointment.getCurrentStatus());
            reasonField.setText(appointment.getVisitPurpose());
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
                id = controller.generateAppointmentIdentifier();
            }
            
            int duration = 30;
            try {
                duration = Integer.parseInt(durationField.getText().trim());
            } catch (NumberFormatException e) {
                duration = 30;
            }
            
            String today = java.time.LocalDate.now().toString();
            
            if (appointment == null) {
                // Creating new appointment
                appointment = new AppointmentModel(
                    id,
                    patientIdField.getText().trim(),
                    clinicianIdField.getText().trim(),
                    facilityIdField.getText().trim(),
                    dateField.getText().trim(),
                    timeField.getText().trim(),
                    duration,
                    typeField.getText().trim(),
                    statusField.getText().trim(),
                    reasonField.getText().trim(),
                    "",
                    today,
                    today
                );
                controller.createAppointment(appointment);
                JOptionPane.showMessageDialog(this, "Appointment created successfully!");
            } else {
                // Editing existing appointment
                appointment.setPatientIdentifier(patientIdField.getText().trim());
                appointment.setClinicianIdentifier(clinicianIdField.getText().trim());
                appointment.setFacilityIdentifier(facilityIdField.getText().trim());
                appointment.setAppointmentDate(dateField.getText().trim());
                appointment.setAppointmentTime(timeField.getText().trim());
                appointment.setDurationInMinutes(duration);
                appointment.setAppointmentCategory(typeField.getText().trim());
                appointment.setCurrentStatus(statusField.getText().trim());
                appointment.setVisitPurpose(reasonField.getText().trim());
                appointment.setLastModificationTimestamp(today);
                
                controller.updateAppointment(appointment);
                JOptionPane.showMessageDialog(this, "Appointment updated successfully!");
            }
            
            wasSaved = true;
            dispose();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error saving appointment: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    public boolean isSaved() {
        return wasSaved;
    }
}
