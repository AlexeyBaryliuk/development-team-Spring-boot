package com.epam.brest.courses.swing.panel.projects_panels;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.service.ProjectsDtoService;
import com.epam.brest.courses.service.ProjectsService;
import com.epam.brest.courses.service.Projects_DevelopersService;
import com.epam.brest.courses.swing.instance_of.DescriptionJDialog;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

public class EditProjectPanel extends AddProjectPanel {

    private static Integer i=1;
    private String item = "";
    private JLabel header;
    private JPanel comboPanel;
    private JComboBox comboBox;
    private List<Developers> developers;
    private ObjectMapper mapper = new ObjectMapper();
    private Projects projects;
    private  String field = "";
    private final ProjectsService projectsService;
    private final ProjectsDtoService projectsDtoService;
    private final Projects_DevelopersService projects_developersService;
    private final ProjectsPanel projectsPanel;
    private final DevelopersService developersService;
    private JButton addDeveloperToProject;

    public EditProjectPanel(ProjectsService projectsService
            , ProjectsDtoService projectsDtoService
            , Projects_DevelopersService projects_developersService, DevelopersService developersService
            , ProjectsPanel projectsPanel) {

        super(projectsService,projectsDtoService,projectsPanel);

        this.projectsService = projectsService;
        this.projectsDtoService = projectsDtoService;
        this.projects_developersService = projects_developersService;
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
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                comboBox = (JComboBox)actionEvent.getSource();
                item = (String)comboBox.getSelectedItem();
            }
        });

        comboPanel = new JPanel(new BorderLayout());

        addDeveloperToProject = new JButton("Add to project");
        addDeveloperToProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!item.equals("")) {
                    int developerId = findId(item);

                    try {
                        projects_developersService.addDeveloperToProjects_Developers(ProjectsPanel.getProjectId(), developerId);

                        JDialog dialog = new DescriptionJDialog("Confirm adding", true);
                        JTextArea textArea = new JTextArea("Developer with developerId = " + developerId + " was added");
                        textArea.setEditable(false);
                        textArea.setLineWrap(true);
                        textArea.setBackground(Color.LIGHT_GRAY);
                        dialog.add(textArea);

                        dialog.setVisible(true);
                    } catch (Exception e) {
                        JDialog dialog = new DescriptionJDialog("Warning", true);
                        dialog.add(new JLabel("Developer with developerId = " + developerId + " already present"));
                        dialog.setVisible(true);
                    }

                }
            }
        });

        JSplitPane splitPaneH = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(comboBox);
        panel.add(addDeveloperToProject);
        splitPaneH.add(panel);
        comboPanel.add(splitPaneH, BorderLayout.NORTH);
        add(comboPanel, BorderLayout.CENTER);

    }

    public String[] createListDevelopers(){
        StringBuilder stringBuilder= new StringBuilder("");

        developers = new ArrayList<>();

        developers = convertDevelopersFromLinked(developersService.findAll());
        int countOfRow = developers.size() + 1;
        String[] arr = new String[countOfRow];
        for (int i = 0; i < countOfRow; i++) {
            if(i == 0){
                arr[i] = "";
            }
            else {
                stringBuilder.append(developers.get(i-1).getDeveloperId())
                        .append(" ")
                        .append(developers.get(i-1)
                                .getFirstName()).append(" ")
                        .append(developers.get(i-1)
                                .getLastName());
                arr[i] = stringBuilder.toString();
                stringBuilder.setLength(0);
            }
        }
        return arr;
    }

    private int findId(String str){
        Integer id = 0;
        String[] arr = str.split("");
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

