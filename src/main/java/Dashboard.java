import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Dashboard extends JFrame {
    private JPanel mainPanel;
    private RecordsTableModel recordsTableModel;
    private JButton createRecordButton;
    private JTable recordsTable;
    private JScrollPane tableScrollPane;
    private BicycleRackDao bicycleRackDao;

    public Dashboard() {
        initializeDao();
        setupFrame();
        initializeComponents();
        setupLayout();
        loadRecords();
    }

    private void initializeDao() {
        bicycleRackDao = new BicycleRackDao(ConfigReader.getStringValue("database.url"));
    }

    private void setupFrame() {
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
    }

    private void initializeComponents() {
        initializeRecordsTable();
        initializeCreateRecordButton();
    }

    private void initializeRecordsTable() {
        recordsTableModel = new RecordsTableModel();
        recordsTable = new JTable(recordsTableModel);
        setupCheckOutButtonColumn(recordsTable, recordsTableModel, bicycleRackDao, this::loadRecords);
        setupEditButtonColumn(recordsTable, recordsTableModel, bicycleRackDao, this::loadRecords);
        tableScrollPane = new JScrollPane(recordsTable);
    }

    private void setupCheckOutButtonColumn(JTable table, RecordsTableModel recordsTableModel, BicycleRackDao bicycleRackDao, Runnable onCheckOut) {
        table.getColumnModel().getColumn(6).setCellRenderer(new JTableComponentRenderer(table.getDefaultRenderer(String.class), recordsTableModel));
        table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(e -> handleCheckOutButtonClick(e, recordsTableModel, bicycleRackDao, onCheckOut)));
    }

    private void setupEditButtonColumn(JTable table, RecordsTableModel recordsTableModel, BicycleRackDao bicycleRackDao, Runnable onEdit) {
        table.getColumnModel().getColumn(7).setCellRenderer(new JTableComponentRenderer(table.getDefaultRenderer(String.class), recordsTableModel));
        table.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(e -> handleEditButtonClick(e, recordsTableModel, bicycleRackDao, onEdit)));
    }

    private void handleCheckOutButtonClick(ActionEvent e, RecordsTableModel recordsTableModel, BicycleRackDao dao, Runnable onCheckOut) {
        int row = Integer.parseInt(e.getActionCommand());
        Record record = recordsTableModel.getRecordAt(row);
        if (record.getCheckOut() == null) {
            dao.checkOutRecord(record.getId());
            onCheckOut.run();
        } else {
            JOptionPane.showMessageDialog(this, "The record has already been checked out.");
        }
    }

    private void handleEditButtonClick(ActionEvent e, RecordsTableModel recordsTableModel, BicycleRackDao dao, Runnable onEdit) {
        int row = Integer.parseInt(e.getActionCommand());
        Record record = recordsTableModel.getRecordAt(row);
        EditRecordDialog editRecordDialog = new EditRecordDialog(this, dao, record, onEdit);
        editRecordDialog.setVisible(true);
    }

    private void initializeCreateRecordButton() {
        createRecordButton = new JButton("Create Record");
        createRecordButton.addActionListener(e -> {
            CreateRecordDialog createRecordDialog = new CreateRecordDialog(this, bicycleRackDao, this::loadRecords);
            createRecordDialog.setVisible(true);
            loadRecords();
        });
    }

    private void setupLayout() {
        mainPanel = new JPanel(new GridBagLayout());
        setContentPane(mainPanel);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(createRecordButton, gbc);

        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(tableScrollPane, gbc);
    }

    private void loadRecords() {
        ArrayList<Record> records = bicycleRackDao.getRecords();
        recordsTableModel.setRecords(records);
    }
}
