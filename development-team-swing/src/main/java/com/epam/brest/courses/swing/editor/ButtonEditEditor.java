package com.epam.brest.courses.swing.editor;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ButtonEditEditor extends DefaultCellEditor {

    private final JButton button;

    public ButtonEditEditor(JCheckBox checkBox, JButton button) {
        super(checkBox);
        this.button = button;
    }

    private int mnemonic;
    private Border focusBorder;
    private Object editorValue;

    /**
     *  Get foreground color of the button when the cell has focus
     *
     *  @return the foreground color
     */
    public Border getFocusBorder()
    {
        return focusBorder;
    }

    /**
     *  The foreground color of the button when the cell has focus
     *
     *  @param focusBorder the foreground color
     */
    public void setFocusBorder(Border focusBorder)
    {
        this.focusBorder = focusBorder;
        button.setBorder( focusBorder );
    }

    public int getMnemonic()
    {
        return mnemonic;
    }

    /**
     *  The mnemonic to activate the button when the cell has focus
     *
     *  @param mnemonic the mnemonic
     */
    public void setMnemonic(int mnemonic)
    {
        this.mnemonic = mnemonic;
        button.setMnemonic(mnemonic);
        button.setMnemonic(mnemonic);
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column)
    {
        if (value == null)
        {
            button.setText( "" );
            button.setIcon( null );
        }
        else if (value instanceof Icon)
        {
            button.setText( "" );
            button.setIcon( (Icon)value );
        }
        else
        {
            button.setText( value.toString() );
            button.setIcon( null );
        }

        this.editorValue = value;
        return button;
    }

    @Override
    public Object getCellEditorValue()
    {
        return editorValue;
    }

}