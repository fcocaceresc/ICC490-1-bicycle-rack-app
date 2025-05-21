import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class JTableComponentRenderer implements TableCellRenderer {
    private final TableCellRenderer defaultRenderer;
    private final RecordsTableModel model;

    public JTableComponentRenderer(TableCellRenderer defaultRenderer, RecordsTableModel model) {
        this.defaultRenderer = defaultRenderer;
        this.model = model;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (column == 6) {
            int modelRow = table.convertRowIndexToModel(row);
            boolean isCheckedOut = model.getRecordAt(modelRow).getCheckOut() != null;
            JButton button = new JButton(value == null ? "Check Out" : value.toString());
            button.setEnabled(!isCheckedOut);
            return button;
        }
        return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
