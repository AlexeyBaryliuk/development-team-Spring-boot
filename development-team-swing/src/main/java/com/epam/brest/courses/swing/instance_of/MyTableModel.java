package com.epam.brest.courses.swing.instance_of;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {


    public MyTableModel() {
        
        super(new Object[][]{},
                new String[]{
                        "projectId","dateAdded","countOfDevelopers", "edit", "delete", "description"
                });
    }
    Class[] types = new Class[]{
            Integer.class
            , String.class
            , String.class
            , JButton.class
            , JButton.class
            , JButton.class
    };
    boolean[] canEdit = new boolean[]{
            false, true, false, true, true, true
    };

    public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
    }

}