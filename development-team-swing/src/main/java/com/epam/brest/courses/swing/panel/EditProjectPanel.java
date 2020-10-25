package com.epam.brest.courses.swing.panel;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.service.ProjectsDtoService;
import com.epam.brest.courses.service.ProjectsService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

public class EditProjectPanel extends AddProjectPanel {

    private static Integer i=1;
    private JLabel header;
    private JPanel comboPanel;
    private JComboBox comboBox;
    private List<Developers> developers;
    private ObjectMapper mapper = new ObjectMapper();
    private Projects projects;
    private  String field = "hhhh";
    private final ProjectsService projectsService;
    private final ProjectsDtoService projectsDtoService;
    private final ProjectsPanel projectsPanel;
    private final DevelopersService developersService;

    public EditProjectPanel(ProjectsService projectsService
            , ProjectsDtoService projectsDtoService
            , DevelopersService developersService
            , ProjectsPanel projectsPanel) {

        super(projectsService,projectsDtoService,projectsPanel);

        this.projectsService = projectsService;
        this.projectsDtoService = projectsDtoService;
        this.projectsPanel = projectsPanel;
        this.developersService = developersService;

        super.header.setText("Edit project");
        super.save.setText("Edit");

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent evt) {
                if(ProjectsPanel.getProjectId() != 0) {
                    if (projectsService.findByDeveloperId(ProjectsPanel.getProjectId()).isPresent()) {

                        projects = projectsService.findByDeveloperId(ProjectsPanel.getProjectId()).get();

                        field = projects.getDescription();
                        changeField(field);

                    }
                }
            }
        });


        super.saveDialog.removeActionListener(super.actionListener);
        super.saveDialog.addActionListener(actionEvent -> {

            projects.setDescription(super.textField.getText());
            projectsService.update(projects);
            goToParent();
        });

        comboBox = new JComboBox(createListDevelopers());
        comboBox.setFont(new Font("Serif", Font.PLAIN, 14));
        comboBox.setAlignmentX(LEFT_ALIGNMENT);
//        comboBox.addActionListener(actionEvent -> {
//
//        });
        comboPanel = new JPanel(new BorderLayout());
        comboPanel.add(comboBox, BorderLayout.PAGE_START);
        add(comboPanel, BorderLayout.CENTER);

    }

    public String[] createListDevelopers(){
        StringBuilder stringBuilder= new StringBuilder("");

        developers = new ArrayList<>();

        developers = convertDevelopersFromLinked(developersService.findAll());
        String[] arr = new String[developers.size()];
        for (int i = 0; i < developers.size(); i++) {
            stringBuilder.append(developers.get(i).getDeveloperId())
                    .append(" ")
                    .append(developers.get(i)
                            .getFirstName()).append(" ")
                    .append(developers.get(i)
                            .getLastName());
            arr[i] = stringBuilder.toString();
            stringBuilder.setLength(0);

        }
        return arr;
    }

    private int findId(String str){
        Integer id = 0;
        String[] arr = str.split(" ");
        try {
            id = Integer.parseInt(arr[0]);
        }
        catch (NumberFormatException e)
        {
            System.out.println("It isn't a number" + e);
        }

        return id;

    }
    public List<Developers> convertDevelopersFromLinked(List<Developers> developers){

        List<Developers> developersList = mapper.convertValue(
                developers,
                new TypeReference<List<Developers>>(){}
        );
        return developersList;
    }
    public void changeField(String field){
        super.textField.setText(field);
    }
}

