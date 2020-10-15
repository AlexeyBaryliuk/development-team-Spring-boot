package com.epam.brest.courses.swing.panel;

import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.service.ProjectsDtoService;
import com.epam.brest.courses.service.ProjectsService;

import javax.swing.*;
import java.awt.*;

public class ProjectsCards extends JPanel {


    private ProjectsPanel projectsPanel;
    private JPanel addProjectPanel;
    private JPanel editProjectPanel;


    private final ProjectsDtoService projectsDtoService;
    private final ProjectsService projectsService;
    private final DevelopersService developersServiceRest;


    public final static String COMMON_PROJECTS = "commonProjects";
    public final static String ADD_PROJECT = "addProject";
    public final static String EDIT_PROJECT = "editProject";

    public ProjectsCards(ProjectsDtoService projectsDtoService
            , ProjectsService projectsService, DevelopersService developersServiceRest) {
        this.projectsDtoService = projectsDtoService;
        this.projectsService = projectsService;
        this.developersServiceRest = developersServiceRest;

        setLayout(new CardLayout());

        projectsPanel = new ProjectsPanel(this.projectsDtoService
                                        , this.projectsService);
        addProjectPanel = new AddProjectPanel(this.projectsService
                                            , this.projectsDtoService
                                            , projectsPanel);
        editProjectPanel = new EditProjectPanel(this.projectsService
                                              , this.projectsDtoService
                                              , this.developersServiceRest, projectsPanel);

        add(projectsPanel, COMMON_PROJECTS);
        add(addProjectPanel, ADD_PROJECT);
        add(editProjectPanel, EDIT_PROJECT);

    }

}
