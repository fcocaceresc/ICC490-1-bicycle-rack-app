import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ButtonEditor extends DefaultCellEditor {
    private final JButton button;
    private final ActionListener actionListener;
    private String label;
    private boolean isPushed;
    private JTable table;
    private int row;

    public ButtonEditor(ActionListener actionListener) {
        super(new JTextField());
        this.actionListener = actionListener;
        button = new JButton();
        button.addActionListener(e -> fireEditingStopped());
        setClickCountToStart(1);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        this.row = row;
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    public void fireEditingStopped() {
        boolean wasPushed = isPushed;
        super.fireEditingStopped();
        if (wasPushed) {
            int currentRow = row;
            ActionEvent actionEvent = new ActionEvent(ButtonEditor.this, ActionEvent.ACTION_PERFORMED, String.valueOf(currentRow));
            actionListener.actionPerformed(actionEvent);
        }
    }
}
