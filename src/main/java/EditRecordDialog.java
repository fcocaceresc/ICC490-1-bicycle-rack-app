import javax.swing.*;
import java.awt.*;

public class EditRecordDialog extends JDialog {
    private final BicycleRackDao bicycleRackDao;
    private final Record record;
    private final Runnable onRecordUpdated;
    private JPanel mainPanel;
    private JLabel studentIdLabel;
    private JTextField studentIdField;
    private JLabel studentNameLabel;
    private JTextField studentNameField;
    private JLabel bicycleDescriptionLabel;
    private JTextField bicycleDescriptionField;
    private JButton updateButton;
    private JButton cancelButton;

    public EditRecordDialog(JFrame parent, BicycleRackDao bicycleRackDao, Record record, Runnable onRecordUpdated) {
        super(parent, "Edit Record");
        this.bicycleRackDao = bicycleRackDao;
        this.record = record;
        this.onRecordUpdated = onRecordUpdated;
        initializeComponents();
        populateFields();
        setupLayout();

        pack();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initializeComponents() {
        initializeStudentIdComponents();
        initializeStudentNameComponents();
        initializeBicycleDescriptionComponents();
        initializeButtons();
    }

    private void initializeStudentIdComponents() {
        studentIdLabel = new JLabel("Student ID:");
        studentIdField = new JTextField(20);
    }

    private void initializeStudentNameComponents() {
        studentNameLabel = new JLabel("Student Name:");
        studentNameField = new JTextField(20);
    }

    private void initializeBicycleDescriptionComponents() {
        bicycleDescriptionLabel = new JLabel("Bicycle Description:");
        bicycleDescriptionField = new JTextField(20);
    }

    private void initializeButtons() {
        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateRecord());
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
    }

    private void populateFields() {
        studentIdField.setText(record.getStudent().getId());
        studentNameField.setText(record.getStudent().getName());
        bicycleDescriptionField.setText(record.getBicycleDescription());
    }

    private void setupLayout() {
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(studentIdLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(studentIdField, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(studentNameLabel, gbc);

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(studentNameField, gbc);

        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(bicycleDescriptionLabel, gbc);

        gbc.gridy = 5;
        gbc.gridwidth = 2;
        mainPanel.add(bicycleDescriptionField, gbc);

        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(updateButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(cancelButton, gbc);

        setContentPane(mainPanel);
    }

    private void updateRecord() {
        String studentId = studentIdField.getText();
        String studentName = studentNameField.getText();
        String bicycleDescription = bicycleDescriptionField.getText();
        try {
            bicycleRackDao.updateRecord(record.getId(), studentId, studentName, bicycleDescription);
            JOptionPane.showMessageDialog(this, "Record updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            onRecordUpdated.run();
            dispose();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
