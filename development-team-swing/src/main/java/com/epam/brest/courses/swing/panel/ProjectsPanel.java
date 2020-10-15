package com.epam.brest.courses.swing.panel;

import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.service.ProjectsDtoService;
import com.epam.brest.courses.service.ProjectsService;
import com.epam.brest.courses.swing.editor.ButtonEditEditor;
import com.epam.brest.courses.swing.instance_of.DateTextField;
import com.epam.brest.courses.swing.instance_of.DescriptionJDialog;
import com.epam.brest.courses.swing.instance_of.MyTableModel;
import com.epam.brest.courses.swing.renderer.ButtonEditRenderer;
import com.epam.brest.courses.swing.renderer.RowRenderer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ProjectsPanel extends JPanel {

    private JTextField textFieldFrom;
    private JTextField textFieldTo;
    private JLabel from;
    private JLabel to;
    private JButton find;
    private JTable projectsData;
    private JButton edit;
    private JButton delete;
    private JButton description;
    private JPanel northPanel;
    private Box contents;
    private JPanel header;
    private JPanel filter;
    private JButton add;
    private JButton refresh;
    private JTable table;
    private ProjectsCards parent;
    private List<ProjectsDto> projectsDtoList ;
    private ObjectMapper mapper = new ObjectMapper();



    private final ProjectsDtoService projectsDtoService;

    private final ProjectsService projectsService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsPanel.class);

    public ProjectsPanel(ProjectsDtoService projectsDtoService, ProjectsService projectsService){
        this.projectsDtoService = projectsDtoService;
        this.projectsService = projectsService;

        northPanel = new JPanel();
        header = new JPanel();
        filter = new JPanel();
        add = new JButton("Add");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                parent = (ProjectsCards) getParent();

                    CardLayout layout = (CardLayout)(parent.getLayout());
                    layout.show(parent, ProjectsCards.ADD_PROJECT);

            }
        });

        refresh = new JButton("Refresh");
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                cleanTable();

                add_row(convertFromLinked(projectsDtoService.countOfDevelopers()));
            }
        });

        edit = new JButton();
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                parent = (ProjectsCards) getParent();

                CardLayout layout = (CardLayout)(parent.getLayout());
                layout.show(parent, parent.EDIT_PROJECT);
            }
        });

        delete = new JButton();
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                DefaultTableModel dm = (DefaultTableModel)table.getModel();

                int row = table.getSelectedRow();
                int projectId = (int)table.getModel().getValueAt(row, 0);
                try {
                    projectsService.delete(projectId);
                    dm.removeRow(row);
                }
                catch (Exception e){
                    LOGGER.debug("The row number {} wasn't removed.", row);
                }
            }
        });

        description = new JButton();
        description.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int c = 0;
                String description = null;
                JPanel textPanel = new JPanel(new FlowLayout());
                JLabel descriptionLable = new JLabel();
                textPanel.add(descriptionLable);

                int row = table.getSelectedRow();
                int projectId = (int)table.getModel().getValueAt(row, 0);
                projectsDtoList = new ArrayList<>();
                projectsDtoList = projectsDtoService.countOfDevelopers();

                projectsDtoList = convertFromLinked(projectsDtoList);
                for (int i = 0; i < projectsDtoList.size(); i++) {
                    if (projectsDtoList.get(i).getProjectId() == projectId){
                        description =  projectsDtoList.get(i).getDescription();
                        descriptionLable.setText(description);

                        break;
                    }

                    c++;
                }

                JDialog dialog = new DescriptionJDialog("Description", true);
                dialog.add(textPanel);
                dialog.setVisible(true);
            }
        });

        MyTableModel model = new MyTableModel();

        table = new JTable();
        table.setModel(model);

        table.getColumn("edit").setCellRenderer(new ButtonEditRenderer());
        table.getColumn("edit").setCellEditor(new ButtonEditEditor(new JCheckBox(), edit));

        table.getColumn("delete").setCellRenderer(new ButtonEditRenderer());
        table.getColumn("delete").setCellEditor(new ButtonEditEditor(new JCheckBox(),delete));

            table.getColumn("description").setCellRenderer(new ButtonEditRenderer());
            table.getColumn("description").setCellEditor(new ButtonEditEditor(new JCheckBox(), description));

                table.getColumn("projectId").setCellRenderer(new RowRenderer());
                table.getColumn("dateAdded").setCellRenderer(new RowRenderer());
                table.getColumn("countOfDevelopers").setCellRenderer(new RowRenderer());

                contents = new Box(BoxLayout.Y_AXIS);
        contents.add(new JScrollPane(table));

        setLayout(new BorderLayout());
        northPanel.setLayout(new BorderLayout());

        header.setLayout(new FlowLayout(FlowLayout.RIGHT));
        header.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        from = new JLabel(" Filter from ");
        textFieldFrom = new DateTextField();
        to = new JLabel(" to ");
        textFieldTo = new DateTextField();
        find = new JButton("Find");
        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                LocalDate dateFrom = localDateFromString(textFieldFrom.getText());
                LocalDate dateTo = localDateFromString(textFieldTo.getText());

                cleanTable();
                add_row(convertFromLinked(projectsDtoService.findAllByDateAddedBetween(dateFrom, dateTo)));
            }
        });

        filter.setLayout(new FlowLayout(FlowLayout.LEFT));
        filter.add(from);
        filter.add(textFieldFrom);
        filter.add(to);
        filter.add(textFieldTo);
        filter.add(find);

        header.add(add);
        header.add(refresh);

        northPanel.add(header, BorderLayout.NORTH);
        northPanel.add(filter, BorderLayout.WEST);

        add(contents, BorderLayout.CENTER);
        add(northPanel,BorderLayout.NORTH);
        add_row(this.projectsDtoService.countOfDevelopers());
    }


    public void add_row(List<ProjectsDto> obj){

       projectsDtoList = convertFromLinked(obj);
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

        Object[] row = new Object[6];
        for (int i = 0; i < obj.size(); i++) {

            row[0] = projectsDtoList.get(i).getProjectId();
            row[1] = stringFromLocalDate(projectsDtoList.get(i).getDateAdded());
            row[2] = projectsDtoList.get(i).getCountOfDevelopers();
            row[3] = "edit";
            row[4] = "delete";
            row[5] = "description";

            tableModel.addRow(row);
        }

    }
//    public ArrayList<ProjectsDto> createObj(){
//        ArrayList<ProjectsDto> projectsDtos = new ArrayList<>();
//        projectsDtos.add(new ProjectsDto(1,"Hello0",localDateFromString("12-03-2018"), 1));
//        projectsDtos.add(new ProjectsDto(2,"Hello1",localDateFromString("13-03-2016"), 5));
//        projectsDtos.add(new ProjectsDto(3,"Hello2",localDateFromString("14-07-2018"), 7));
//        projectsDtos.add(new ProjectsDto(4,"Hello2",localDateFromString("18-07-2018"), 7));
//        return projectsDtos;
//    }

    public List<ProjectsDto> convertFromLinked(List<ProjectsDto> projectsList){

        List<ProjectsDto> projectsDtoList = mapper.convertValue(
                projectsList,
                new TypeReference<List<ProjectsDto>>(){}
        );
            return projectsDtoList;
    }

    public LocalDate localDateFromString(String dateS){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        formatter = formatter.withLocale(Locale.ENGLISH);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        LocalDate date = LocalDate.parse(dateS, formatter);
        return date;
    }
    public String stringFromLocalDate(LocalDate date){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedString = date.format(formatter);
        return formattedString;
    }

    public void cleanTable(){

        DefaultTableModel dm = (DefaultTableModel)table.getModel();
        dm.getDataVector().removeAllElements();
    }
}
