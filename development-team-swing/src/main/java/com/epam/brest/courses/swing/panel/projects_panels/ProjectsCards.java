package com.epam.brest.courses.swing.panel.projects_panels;

import ch.qos.logback.core.Layout;
import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.service.ProjectsDtoService;
import com.epam.brest.courses.service.ProjectsService;
import com.epam.brest.courses.service.Projects_DevelopersService;

import javax.swing.*;
import java.awt.*;

public class ProjectsCards extends JPanel {


    private ProjectsPanel projectsPanel;
    private JPanel addProjectPanel;
    private JPanel editProjectPanel;


    private final ProjectsDtoService projectsDtoService;
    private final ProjectsService projectsService;
    private final DevelopersService developersServiceRest;
    private final Projects_DevelopersService projects_developersService;


    public final static String COMMON_PROJECTS = "commonProjects";
    public final static String ADD_PROJECT = "addProject";
    public final static String EDIT_PROJECT = "editProject";

    public ProjectsCards(ProjectsDtoService projectsDtoService
            , ProjectsService projectsService
            , DevelopersService developersServiceRest
            , Projects_DevelopersService projects_developersService) {
        this.projectsDtoService = projectsDtoService;
        this.projectsService = projectsService;
        this.developersServiceRest = developersServiceRest;
        this.projects_developersService = projects_developersService;

        setLayout(new CardLayout());

        projectsPanel = new ProjectsPanel(this.projectsDtoService
                , this.projectsService);
        addProjectPanel = new AddProjectPanel(this.projectsService
                , this.projectsDtoService
                , projectsPanel);
        editProjectPanel = new EditProjectPanel(this.projectsService
                , this.projectsDtoService
                , this.projects_developersService, this.developersServiceRest, projectsPanel);

        add(projectsPanel, COMMON_PROJECTS);
        add(addProjectPanel, ADD_PROJECT);
        add(editProjectPanel, EDIT_PROJECT);

    }

}
