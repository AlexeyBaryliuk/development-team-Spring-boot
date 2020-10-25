package com.epam.brest.courses.swing.renderer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonEditRenderer extends JButton implements TableCellRenderer {

    private Border focusBorder;
    private JButton renderButton;

    public ButtonEditRenderer() {

        renderButton = new JButton();
        focusBorder = new LineBorder(Color.BLUE);
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        if (isSelected)
        {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        }
        else
        {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        if (hasFocus)
        {
            renderButton.setBorder( focusBorder );
        }
        else
        {
            renderButton.setBorder( focusBorder  );
        }

        if (value == null)
        {
            renderButton.setText( "" );
            renderButton.setIcon( null );
        }
        else if (value instanceof Icon)
        {
            renderButton.setText( "" );
            renderButton.setIcon( (Icon)value );
        }
        else
        {
            renderButton.setText( value.toString() );
            renderButton.setIcon( null );
        }

        return renderButton;
    }
}
