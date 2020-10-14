package com.epam.brest.courses.swing.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BarPanel extends JPanel implements ActionListener {

    private JButton projects;
    private JButton developers;
    private FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
    public BarPanel(){

        projects = new JButton("Projects");
        developers = new JButton("Developers");

        setLayout(flowLayout);
        add(projects);
        add(developers);
        setBackground(Color.GRAY);
        setForeground(Color.cyan);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {


    }
}
