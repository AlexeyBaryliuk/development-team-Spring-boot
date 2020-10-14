package com.epam.brest.courses.swing.editor;

import javax.swing.*;
import java.awt.*;

public class ButtonEditEditor extends DefaultCellEditor {

    private final JButton button;
    private String label;

    public ButtonEditEditor(JCheckBox checkBox, JButton button) {
        super(checkBox);
        this.button = button;
    }

    public Component getTableCellEditorComponent(JTable table
            , Object value
            , boolean isSelected
            , int row
            , int column) {
        System.out.println("+++++++++++" + value.toString());
        label = value.toString();
        button.setText(label);
        return button;

    }

    public Object getCellEditorValue() {
        return label;
    }

}
