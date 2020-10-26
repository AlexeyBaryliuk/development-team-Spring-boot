package com.epam.brest.courses.swing.instance_of;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DevelopersTableModel extends DefaultTableModel {

    public DevelopersTableModel() {

        super(new Object[][]{},
                new String[]{
                        "developerId","FirstName","LastName", "edit", "delete"
                });
    }
    Class[] types = new Class[]{
            Integer.class
            , String.class
            , String.class
            , JButton.class
            , JButton.class

    };
    boolean[] canEdit = new boolean[]{
            false, false, false, true, true
    };

    public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
    }
}
