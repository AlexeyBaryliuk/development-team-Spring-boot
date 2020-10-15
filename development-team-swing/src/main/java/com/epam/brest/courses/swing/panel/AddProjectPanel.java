package com.epam.brest.courses.swing.panel;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.ProjectsDtoService;
import com.epam.brest.courses.service.ProjectsService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class AddProjectPanel extends JPanel{

    private JPanel northPanel;
    private JPanel headerPanel;
    protected JLabel header;
    private JTextField textField;
    private JPanel textBlock;
    private JPanel buttonBlock;
    private Box text;
    private JButton cancel;
    private JButton save;
    private JLabel label;

    private final ProjectsService projectsService;
    private final ProjectsDtoService projectsDtoService;
    private final ProjectsPanel projectsPanel;


    public AddProjectPanel(ProjectsService projectsService
            , ProjectsDtoService projectsDtoService, ProjectsPanel projectsPanel){
        this.projectsService = projectsService;
        this.projectsDtoService = projectsDtoService;
        this.projectsPanel = projectsPanel;


        setLayout(new BorderLayout());
        northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());

        headerPanel = new JPanel();
        headerPanel.setMinimumSize(new Dimension(300, 100));
        headerPanel.setBackground(Color.lightGray);
        headerPanel.setSize(500,500);
        header = new JLabel("NEW PROJECT");
        header.setFont(new Font("Serif", Font.PLAIN, 14));

        JPanel outer = new JPanel();//this will be our grey area
        outer.setLayout(new BoxLayout(outer, BoxLayout.X_AXIS));
        JPanel inner = new JPanel();//here the drawing area
        Dimension receiptSize = new Dimension(400, 50);//making a fixed size for the drawing area
        inner.setLayout(new GridBagLayout());
        inner.setPreferredSize(receiptSize);
        inner.setMinimumSize(receiptSize);
        inner.setMaximumSize(receiptSize);
        inner.setBackground(Color.GRAY);//and making the drawing area white, just for clarity in this example
        inner.setForeground(Color.white);
        inner.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        inner.add(header);

        outer.add(Box.createHorizontalGlue());
        outer.add(inner);
        outer.add(Box.createHorizontalGlue());

        headerPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        northPanel.add(outer, BorderLayout.PAGE_START);

        textBlock = new JPanel(new BorderLayout());
        textBlock.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        text = new Box(BoxLayout.Y_AXIS);
            textField = new JTextField();
                text.add(textField);
            label = new JLabel("Description");

            cancel = new JButton("Cancel");
            cancel.addActionListener(actionEvent -> {

                goToParent();


            });
        save = new JButton("Save");

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JDialog dialog = createDialog("Confirm save", true);
                dialog.setVisible(true);
            }
        });

        textBlock.add(label, BorderLayout.PAGE_START);
        textBlock.add(textField, BorderLayout.CENTER);

        buttonBlock = new JPanel(new FlowLayout());
            buttonBlock.add(cancel);
            buttonBlock.add(save);
        textBlock.add(buttonBlock, BorderLayout.LINE_END);

        northPanel.add(textBlock, BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);

    }

    private JDialog createDialog(String title, boolean modal)
    {
        final JDialog dialog = new JDialog(new JDialog(), title, modal);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setSize(200, 150);
        JPanel buttonPane = new JPanel();
        JButton cancel = new JButton("Close");
        cancel.setBackground(Color.white);
        buttonPane.add(cancel);

        dialog.add(buttonPane, BorderLayout.PAGE_END);
        cancel.addActionListener(actionEvent -> dialog.setVisible(false));

        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                Projects project = new Projects();
                project.setDescription(textField.getText());
                projectsService.create(project);

                projectsPanel.cleanTable();
                projectsPanel.add_row(projectsPanel.convertFromLinked
                                     (projectsDtoService.countOfDevelopers()));
                goToParent();
            }
        });

        save.setBackground(Color.white);
        save.addActionListener(actionEvent -> dialog.setVisible(false));
        buttonPane.add(save);

        return dialog;
    }

    public void goToParent(){

        ProjectsCards parent = (ProjectsCards) getParent();
        CardLayout layout = (CardLayout)(parent.getLayout());
        layout.show(parent, ProjectsCards.COMMON_PROJECTS);
        textField.setText("");
    }
}
