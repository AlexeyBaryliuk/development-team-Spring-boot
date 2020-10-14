package com.epam.brest.courses.swing.panel;

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
    private JLabel footer;

    private CardLayout cardLayout = new CardLayout();
    private BorderLayout borderLayout = new BorderLayout();

    public DevelopmentTeam(){

        super("Development team");
        parent = new JPanel();

        parent.setLayout(borderLayout);
        barPanel = new BarPanel();
        parent.add(barPanel,BorderLayout.NORTH);

        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);
        parent.add(cardPanel, BorderLayout.CENTER);

        projectsCards = new ProjectsCards();
        cardPanel.add(projectsCards);

        ImageIcon icon = createIcon("../../../../../../img/footer.png");
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
