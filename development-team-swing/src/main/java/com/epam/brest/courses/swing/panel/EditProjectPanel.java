package com.epam.brest.courses.swing.panel;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.service.ProjectsDtoService;
import com.epam.brest.courses.service.ProjectsService;

import javax.swing.*;
import java.util.List;

public class EditProjectPanel extends AddProjectPanel {

    private static Integer i=1;
    private JLabel header;
    private JPanel comboPanel;
    private JComboBox comboBox;
    private List<Developers> developers;

    public EditProjectPanel(ProjectsService projectsService
            , ProjectsDtoService projectsDtoService
            , DevelopersService developersServiceRest
            , ProjectsPanel projectsPanel) {

        super(projectsService,projectsDtoService,projectsPanel);


        super.header.setText("Edit project");
    }

//    public EditProjectPanel(){
//
//        super.header.setText("Edit project");
//
//
//
//        comboBox = new JComboBox(createListDevelopers());
//        comboBox.setFont(new Font("Serif", Font.PLAIN, 14));
//        comboBox.setAlignmentX(LEFT_ALIGNMENT);
////        comboBox.addActionListener(actionEvent -> {
////
////        });
//        comboPanel = new JPanel(new BorderLayout());
//        comboPanel.add(comboBox, BorderLayout.PAGE_START);
//        add(comboPanel, BorderLayout.CENTER);
//
//
//
//    }
//
//    public String[] createListDevelopers(){
//        StringBuilder stringBuilder= new StringBuilder("");
//        String[] arr = new String[3];
//        developers = new ArrayList<>();
//
//        developers = developersServiceRest.findAll();
//        for (int i = 0; i < developers.size(); i++) {
//            stringBuilder.append(developers.get(i).getDeveloperId())
//                    .append(" ")
//                    .append(developers.get(i)
//                    .getFirstName()).append(" ")
//                    .append(developers.get(i)
//                    .getLastName());
//            arr[i] = stringBuilder.toString();
//
//        }
//        return arr;
//    }
//
//    private int findId(String str){
//        Integer id = 0;
//        String[] arr = str.split(" ");
//        try {
//            id = Integer.parseInt(arr[0]);
//        }
//        catch (NumberFormatException e)
//        {
//            System.out.println("It isn't a number" + e);
//        }
//
//       return id;
//
//    }

}
