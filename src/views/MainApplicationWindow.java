package views;
import controllers.DataController;
import models.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class MainApplicationWindow extends JFrame {
    private DataController dataController;
    private JTabbedPane mainTabbedPane;
    private DefaultTableModel patientTableModel, appointmentTableModel, 
                              prescriptionTableModel, referralTableModel;
    private JTable patientTable, appointmentTable, prescriptionTable, referralTable;
    
    public MainApplicationWindow(DataController controller) {
        this.dataController = controller;
        setTitle("Healthcare System - Utsav Prajapati");
        setSize(1150, 680);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initialize();
        refreshAllData();
    }
    
    private void initialize() {
        mainTabbedPane = new JTabbedPane();
        mainTabbedPane.addTab("Patients", createPatientPanel());
        mainTabbedPane.addTab("Appointments", createAppointmentPanel());
        mainTabbedPane.addTab("Prescriptions", createPrescriptionPanel());
        mainTabbedPane.addTab("Referrals", createReferralPanel());
        add(mainTabbedPane);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveMenuItem = new JMenuItem("Save All Data");
        saveMenuItem.addActionListener(e -> {
            if (dataController.persistAllData()) {
                JOptionPane.showMessageDialog(this, "Data saved successfully!");
            }
        });
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }
    
    private JPanel createPatientPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "First Name", "Last Name", "DOB", "NHS", "Gender", "Email"};
        patientTableModel = new DefaultTableModel(columns, 0);
        patientTable = new JTable(patientTableModel);
        panel.add(new JScrollPane(patientTable), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        
        addButton.addActionListener(e -> {
            PatientDialog dialog = new PatientDialog(this, null, dataController);
            dialog.setVisible(true);
            if (dialog.isSaved()) {
                refreshPatients();
                dataController.persistAllData();
            }
        });
        
        editButton.addActionListener(e -> {
            int row = patientTable.getSelectedRow();
            if (row >= 0) {
                String id = (String) patientTableModel.getValueAt(row, 0);
                PatientModel patient = dataController.getPatientByIdentifier(id);
                if (patient != null) {
                    PatientDialog dialog = new PatientDialog(this, patient, dataController);
                    dialog.setVisible(true);
                    if (dialog.isSaved()) {
                        refreshPatients();
                        dataController.persistAllData();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a patient to edit");
            }
        });
        
        deleteButton.addActionListener(e -> {
            int row = patientTable.getSelectedRow();
            if (row >= 0) {
                String id = (String) patientTableModel.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this patient?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dataController.deletePatient(id);
                    refreshPatients();
                    dataController.persistAllData();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a patient to delete");
            }
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }
    
    private JPanel createAppointmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Patient", "Clinician", "Date", "Time", "Type", "Status"};
        appointmentTableModel = new DefaultTableModel(columns, 0);
        appointmentTable = new JTable(appointmentTableModel);
        panel.add(new JScrollPane(appointmentTable), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        
        addButton.addActionListener(e -> {
            AppointmentDialog dialog = new AppointmentDialog(this, null, dataController);
            dialog.setVisible(true);
            if (dialog.isSaved()) {
                refreshAppointments();
                dataController.persistAllData();
            }
        });
        
        editButton.addActionListener(e -> {
            int row = appointmentTable.getSelectedRow();
            if (row >= 0) {
                String id = (String) appointmentTableModel.getValueAt(row, 0);
                AppointmentModel appointment = dataController.getAppointmentByIdentifier(id);
                if (appointment != null) {
                    AppointmentDialog dialog = new AppointmentDialog(this, appointment, dataController);
                    dialog.setVisible(true);
                    if (dialog.isSaved()) {
                        refreshAppointments();
                        dataController.persistAllData();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an appointment to edit");
            }
        });
        
        deleteButton.addActionListener(e -> {
            int row = appointmentTable.getSelectedRow();
            if (row >= 0) {
                String id = (String) appointmentTableModel.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this appointment?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dataController.deleteAppointment(id);
                    refreshAppointments();
                    dataController.persistAllData();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an appointment to delete");
            }
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }
    
    private JPanel createPrescriptionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Patient", "Clinician", "Medication", "Dosage", "Status"};
        prescriptionTableModel = new DefaultTableModel(columns, 0);
        prescriptionTable = new JTable(prescriptionTableModel);
        panel.add(new JScrollPane(prescriptionTable), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        
        addButton.addActionListener(e -> {
            PrescriptionDialog dialog = new PrescriptionDialog(this, null, dataController);
            dialog.setVisible(true);
            if (dialog.isSaved()) {
                refreshPrescriptions();
                dataController.persistAllData();
            }
        });
        
        editButton.addActionListener(e -> {
            int row = prescriptionTable.getSelectedRow();
            if (row >= 0) {
                String id = (String) prescriptionTableModel.getValueAt(row, 0);
                PrescriptionModel prescription = dataController.getPrescriptionByIdentifier(id);
                if (prescription != null) {
                    PrescriptionDialog dialog = new PrescriptionDialog(this, prescription, dataController);
                    dialog.setVisible(true);
                    if (dialog.isSaved()) {
                        refreshPrescriptions();
                        dataController.persistAllData();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a prescription to edit");
            }
        });
        
        deleteButton.addActionListener(e -> {
            int row = prescriptionTable.getSelectedRow();
            if (row >= 0) {
                String id = (String) prescriptionTableModel.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this prescription?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dataController.deletePrescription(id);
                    refreshPrescriptions();
                    dataController.persistAllData();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a prescription to delete");
            }
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }
    
    private JPanel createReferralPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Patient", "From", "To", "Urgency", "Status"};
        referralTableModel = new DefaultTableModel(columns, 0);
        referralTable = new JTable(referralTableModel);
        panel.add(new JScrollPane(referralTable), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add");
        JButton sendButton = new JButton("Send");
        
        addButton.addActionListener(e -> {
            ReferralDialog dialog = new ReferralDialog(this, dataController);
            dialog.setVisible(true);
            refreshReferrals();
            dataController.persistAllData();
        });
        
        sendButton.addActionListener(e -> {
            int row = referralTable.getSelectedRow();
            if (row >= 0) {
                String id = (String) referralTableModel.getValueAt(row, 0);
                for (ReferralModel ref : dataController.getAllReferrals()) {
                    if (ref.getReferralIdentifier().equals(id)) {
                        dataController.transmitReferral(ref);
                        JOptionPane.showMessageDialog(this, "Referral transmitted successfully!");
                        refreshReferrals();
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a referral to send");
            }
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(sendButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }
    
    private void refreshAllData() {
        refreshPatients();
        refreshAppointments();
        refreshPrescriptions();
        refreshReferrals();
    }
    
    private void refreshPatients() {
        patientTableModel.setRowCount(0);
        for (PatientModel p : dataController.getAllPatients()) {
            patientTableModel.addRow(new Object[]{
                p.getPatientIdentifier(),
                p.getFirstName(),
                p.getLastName(),
                p.getBirthDate(),
                p.getNationalHealthNumber(),
                p.getGender(),
                p.getEmailAddress()
            });
        }
    }
    
    private void refreshAppointments() {
        appointmentTableModel.setRowCount(0);
        for (AppointmentModel a : dataController.getAllAppointments()) {
            appointmentTableModel.addRow(new Object[]{
                a.getAppointmentIdentifier(),
                a.getPatientIdentifier(),
                a.getClinicianIdentifier(),
                a.getAppointmentDate(),
                a.getAppointmentTime(),
                a.getAppointmentCategory(),
                a.getCurrentStatus()
            });
        }
    }
    
    private void refreshPrescriptions() {
        prescriptionTableModel.setRowCount(0);
        for (PrescriptionModel p : dataController.getAllPrescriptions()) {
            prescriptionTableModel.addRow(new Object[]{
                p.getPrescriptionIdentifier(),
                p.getPatientIdentifier(),
                p.getClinicianIdentifier(),
                p.getMedicationName(),
                p.getDosageAmount(),
                p.getPrescriptionStatus()
            });
        }
    }
    
    private void refreshReferrals() {
        referralTableModel.setRowCount(0);
        for (ReferralModel r : dataController.getAllReferrals()) {
            referralTableModel.addRow(new Object[]{
                r.getReferralIdentifier(),
                r.getPatientIdentifier(),
                r.getReferringClinicianIdentifier(),
                r.getReferredToClinicianIdentifier(),
                r.getUrgencyCategory(),
                r.getReferralStatus()
            });
        }
    }
}
