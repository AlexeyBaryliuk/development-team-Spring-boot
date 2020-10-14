package com.epam.brest.courses.swing.renderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class RowRenderer implements TableCellRenderer {

    public static final DefaultTableCellRenderer DEFAULT_RENDERER =
            new DefaultTableCellRenderer();

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {

        Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table
                , value, isSelected, hasFocus, row, column);

        Color foreground, background;

        if (isSelected) {
            foreground = Color.BLACK;
            background = Color.LIGHT_GRAY;
        } else {
            if (row % 2 == 0) {
                foreground = Color.gray;
                background = Color.WHITE;
            } else {
                foreground = Color.WHITE;
                background = Color.gray;
            }
        }
        renderer.setForeground(foreground);
        renderer.setBackground(background);
        return renderer;

    }
}