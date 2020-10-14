package com.epam.brest.courses.swing.panel;

import com.epam.brest.courses.model.Developers;
import org.apache.commons.lang.RandomStringUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static com.epam.brest.courses.model.constants.DeveloperConstants.FIRSTNAME_SIZE;
import static com.epam.brest.courses.model.constants.DeveloperConstants.LASTNAME_SIZE;

public class EditProjectPanel extends AddProjectPanel {

    private static Integer i=1;
    private JLabel header;
    private JPanel comboPanel;
    private JComboBox comboBox;


    public EditProjectPanel(){

        super.header.setText("Edit project");



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
        String[] arr = new String[3];
        ArrayList<Developers> developers = new ArrayList<>();

        developers.add(newDeveloper());

        developers.add(newDeveloper());
        developers.add(newDeveloper());
        for (int i = 0; i < developers.size(); i++) {
            stringBuilder.append(developers.get(i).getDeveloperId())
                    .append(" ")
                    .append(developers.get(i)
                    .getFirstName()).append(" ")
                    .append(developers.get(i)
                    .getLastName());
            arr[i] = stringBuilder.toString();

        }
        return arr;
    }

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
    private static Developers newDeveloper(){
        Developers developer = new Developers();
        developer.setDeveloperId(i);
        String firstName = RandomStringUtils.randomAlphabetic(LASTNAME_SIZE);
        developer.setFirstName(firstName);
        String lastName = RandomStringUtils.randomAlphabetic(FIRSTNAME_SIZE);
        developer.setLastName(lastName);
        i++;
        return developer;
    }
}
