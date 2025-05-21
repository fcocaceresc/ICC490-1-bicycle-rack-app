import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class RecordsTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Student ID", "Student Name", "Bicycle Description", "Check In", "Check Out"};
    private final ArrayList<Record> records = new ArrayList<>();

    public void setRecords(ArrayList<Record> records) {
        this.records.clear();
        this.records.addAll(records);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return records.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Record record = records.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> record.getId();
            case 1 -> record.getStudent().getId();
            case 2 -> record.getStudent().getName();
            case 3 -> record.getBicycleDescription();
            case 4 -> record.getCheckIn();
            case 5 -> record.getCheckOut();
            default -> null;
        };
    }
}
