import javax.swing.*;
import java.awt.*;

public class CreateRecordDialog extends JDialog {
    private JPanel mainPanel;
    private JLabel studentIdLabel;
    private JTextField studentIdField;
    private JLabel studentNameLabel;
    private JTextField studentNameField;
    private JLabel bicycleDescriptionLabel;
    private JTextField bicycleDescriptionField;
    private JButton createButton;
    private JButton cancelButton;

    public CreateRecordDialog(JFrame parent) {
        super(parent, "Create Record");
        initializeComponents();
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
        createButton = new JButton("Create");
        cancelButton = new JButton("Cancel");
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
        mainPanel.add(createButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(cancelButton, gbc);

        setContentPane(mainPanel);
    }
}
