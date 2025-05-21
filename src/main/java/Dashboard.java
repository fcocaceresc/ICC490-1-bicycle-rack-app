import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Dashboard extends JFrame {
    private JPanel mainPanel;
    private RecordsTableModel recordsTableModel;
    private JTable recordsTable;
    private JScrollPane tableScrollPane;
    private BicycleRackDao bicycleRackDao;

    public Dashboard() {
        initializeDao();
        setupFrame();
        initializeRecordsTable();
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

    private void initializeRecordsTable() {
        recordsTableModel = new RecordsTableModel();
        recordsTable = new JTable(recordsTableModel);
        tableScrollPane = new JScrollPane(recordsTable);
    }

    private void setupLayout() {
        mainPanel = new JPanel(new GridBagLayout());
        setContentPane(mainPanel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
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
