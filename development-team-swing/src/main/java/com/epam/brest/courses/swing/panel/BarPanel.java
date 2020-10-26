package com.epam.brest.courses.swing.panel;

import com.epam.brest.courses.swing.panel.developers_panels.DevelopersCards;
import com.epam.brest.courses.swing.panel.projects_panels.ProjectsCards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BarPanel extends JPanel{

    private JButton projects;
    private JButton developers;
    private JPanel projectsPanel;
    private JPanel developersPanel;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);

    public BarPanel(JPanel projectsPanel
            , JPanel developersPanel
            , CardLayout layout
            , JPanel cardPanel){

        this.projectsPanel = projectsPanel;
        this.developersPanel = developersPanel;
        this.cardLayout = layout;
        this.cardPanel = cardPanel;

        projects = new JButton("Projects");
        projects.addActionListener(actionEvent -> {
            cardLayout.show(cardPanel, DevelopmentTeam.PROJECTS_PANEL);
        });

        developers = new JButton("Developers");
        developers.addActionListener(actionEvent -> {
            cardLayout.show(cardPanel, DevelopmentTeam.DEVELOPERS_PANEL);
        });

        setLayout(flowLayout);
        add(projects);
        add(developers);
        setBackground(Color.GRAY);
        setForeground(Color.cyan);

    }
}
