package com.epam.brest.courses.swing.panel;

import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.service.ProjectsDtoService;
import com.epam.brest.courses.service.ProjectsService;
import com.epam.brest.courses.service.Projects_DevelopersService;
import com.epam.brest.courses.swing.panel.developers_panels.DevelopersCards;
import com.epam.brest.courses.swing.panel.projects_panels.AddProjectPanel;
import com.epam.brest.courses.swing.panel.projects_panels.ProjectsCards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

@Component
public class DevelopmentTeam extends JFrame {

    private JPanel parent;
    private JPanel barPanel;
    private JPanel cardPanel;
    private JPanel projectsCards;
    private JPanel developersCards;
    private JLabel footer;
    public static final String PROJECTS_PANEL= "pro";
    public static final String DEVELOPERS_PANEL= "dev";

    @Autowired
    private final ProjectsDtoService projectsDtoService;
    @Autowired
    private final ProjectsService projectsService;
    @Autowired
    private final DevelopersService developersServiceRest;
    @Autowired
    private final Projects_DevelopersService projects_developersService;

    private CardLayout cardLayout = new CardLayout();
    private BorderLayout borderLayout = new BorderLayout();

    public DevelopmentTeam(ProjectsDtoService projectsDtoService
            , ProjectsService projectsService
            , DevelopersService developersServiceRest
            , Projects_DevelopersService projects_developersService){

        super("Development team");
        this.projectsDtoService = projectsDtoService;
        this.projectsService = projectsService;
        this.developersServiceRest = developersServiceRest;
        this.projects_developersService = projects_developersService;

        parent = new JPanel();

        parent.setLayout(borderLayout);


        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);
        parent.add(cardPanel, BorderLayout.CENTER);

        projectsCards = new ProjectsCards(this.projectsDtoService
                , this.projectsService
                , this.developersServiceRest
                , this.projects_developersService);
        cardPanel.add(projectsCards, PROJECTS_PANEL);

        developersCards = new DevelopersCards(this.developersServiceRest);
        cardPanel.add(developersCards,DEVELOPERS_PANEL);

        barPanel = new BarPanel(projectsCards
                              , developersCards
                              , cardLayout
                              , cardPanel);

        parent.add(barPanel,BorderLayout.NORTH);

        ImageIcon icon = createIcon("../../../../../../../img/footer.png");
        footer = new JLabel("2020", icon, JLabel.CENTER);

        parent.add(footer,BorderLayout.SOUTH);

        add(parent);
    }

    protected static ImageIcon createIcon(String path) {

        URL imgURL = AddProjectPanel.class.getResource(path);

        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {

            System.err.println("File not found " + path);
            return null;
        }
    }
}
