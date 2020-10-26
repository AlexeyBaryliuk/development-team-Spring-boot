package com.epam.brest.courses.swing.panel.developers_panels;

import com.epam.brest.courses.service.DevelopersService;

import javax.swing.*;
import java.awt.*;

public class DevelopersCards extends JPanel {

    private DevelopersPanel developersPanel;
    private JPanel addDeveloperPanel;
    private JPanel editDeveloperPanel;

    private final DevelopersService developersServiceRest;

    public final static String COMMON_DEVELOPERS = "commonDevelopers";
    public final static String ADD_DEVELOPERS = "addDevelopers";
    public final static String EDIT_DEVELOPERS = "editDevelopers";

    public DevelopersCards(DevelopersService developersServiceRest) {
        this.developersServiceRest = developersServiceRest;

        setLayout(new CardLayout());

        developersPanel = new DevelopersPanel(this.developersServiceRest);
        addDeveloperPanel = new AddDevelopersPanel(this.developersServiceRest
                                                  ,developersPanel);
//        editDeveloperPanel = new EditDeveloperPanel(this.developersServiceRest);

        add(developersPanel, COMMON_DEVELOPERS);
        add(addDeveloperPanel, ADD_DEVELOPERS);
//        add(editDeveloperPanel, EDIT_DEVELOPERS);
    }

}
