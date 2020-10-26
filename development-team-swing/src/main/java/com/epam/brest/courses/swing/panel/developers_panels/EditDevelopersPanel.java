package com.epam.brest.courses.swing.panel.developers_panels;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.service.DevelopersService;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class EditDevelopersPanel extends AddDevelopersPanel{

    private Developers developer;
    private String textLastName;
    private String textFirstName;

    private final DevelopersService developersService;
    private final DevelopersPanel developersPanel;

    public EditDevelopersPanel(DevelopersService developersService
            , DevelopersPanel developersPanel) {
        super(developersService, developersPanel);
        this.developersService = developersService;
        this.developersPanel = developersPanel;

        super.header.setText("Edit developer");
        super.save.setText("Edit");

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent evt) {
                if(DevelopersPanel.getDeveloperId() != 0) {
                    if (developersService.findByDeveloperId(DevelopersPanel.getDeveloperId()).isPresent()) {

                        developer = developersService.findByDeveloperId(DevelopersPanel.getDeveloperId()).get();

                        textFirstName = developer.getFirstName();
                        textLastName = developer.getLastName();
                        changeFields(textFirstName, textLastName);

                    }
                }
            }
        });
        super.saveDialog.removeActionListener(super.actionListener);
        super.saveDialog.addActionListener(actionEvent -> {

            developer.setFirstName(super.textFirstName.getText());
            developer.setLastName(super.textLastName.getText());
            developersService.update(developer);
            goToParent();
        });


    }

    public void changeFields(String firsName, String lastName){
        super.textFirstName.setText(firsName);
        super.textLastName.setText(lastName);
    }

}
