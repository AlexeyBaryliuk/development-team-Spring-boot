package com.epam.brest.courses.swing.panel;

import javax.swing.*;
import java.awt.*;

public class ProjectsCards extends JPanel {


    private JPanel projectsPanel;
    private JPanel addProjectPanel;
    private JPanel editProjectPanel;

    public final static String COMMON_PROJECTS = "commonProjects";
    public final static String ADD_PROJECT = "addProject";
    public final static String EDIT_PROJECT = "editProject";

    public ProjectsCards() {

        setLayout(new CardLayout());

        projectsPanel = new ProjectsPanel();
        addProjectPanel = new AddProjectPanel();
        editProjectPanel = new EditProjectPanel();

        add(projectsPanel, COMMON_PROJECTS);
        add(addProjectPanel, ADD_PROJECT);
        add(editProjectPanel, EDIT_PROJECT);

    }

}
