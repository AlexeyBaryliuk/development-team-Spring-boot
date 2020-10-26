package com.epam.brest.courses.swing.panel.projects_panels;

import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.service.ProjectsDtoService;
import com.epam.brest.courses.service.ProjectsService;
import com.epam.brest.courses.swing.editor.ButtonEditEditor;
import com.epam.brest.courses.swing.instance_of.DateTextField;
import com.epam.brest.courses.swing.instance_of.DescriptionJDialog;
import com.epam.brest.courses.swing.instance_of.ProjectsTableModel;
import com.epam.brest.courses.swing.renderer.ButtonEditRenderer;
import com.epam.brest.courses.swing.renderer.RowRenderer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
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
    private static Integer projectEditId = 0;


    private final ProjectsDtoService projectsDtoService;

    private final ProjectsService projectsService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsPanel.class);

    public ProjectsPanel(ProjectsDtoService projectsDtoService
                        , ProjectsService projectsService){
        this.projectsDtoService = projectsDtoService;
        this.projectsService = projectsService;

        northPanel = new JPanel();
        header = new JPanel();
        filter = new JPanel();
        delete = new JButton();
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

                refreshTable();
            }
        });

        edit = new JButton();
        edit.addActionListener(actionEvent -> {
            parent = (ProjectsCards) getParent();

            int row = table.getSelectedRow();
            projectEditId = (int)table.getModel().getValueAt(row, 0);

            if (table.isEditing())
                table.getCellEditor().stopCellEditing();

            CardLayout layout = (CardLayout)(parent.getLayout());
            layout.show(parent, ProjectsCards.EDIT_PROJECT);

        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent evt) {

                refreshTable();
            }
        });
        description = new JButton();
        description.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int c = 0;
                String description = null;
                JPanel textPanel = new JPanel();

                JTextArea descriptionLable = new JTextArea();
                descriptionLable.setEditable(false);
                descriptionLable.setLineWrap(true);
                descriptionLable.setBackground(Color.LIGHT_GRAY);

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
                dialog.add(descriptionLable);
                dialog.setVisible(true);
            }
        });

        ProjectsTableModel model = new ProjectsTableModel();

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

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultTableModel dm = (DefaultTableModel)table.getModel();

                int row = table.getSelectedRow();
                int projectId = (int)table.getModel().getValueAt(row, 0);
                try {
                    projectsService.delete(projectId);

                    if (table.isEditing())
                        table.getCellEditor().stopCellEditing();

                    ((DefaultTableModel)table.getModel()).removeRow(row);

                }
                catch (Exception ex){
                    LOGGER.debug("The row number {} wasn't removed.", row);
                }
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
            System.out.println("Row++++++" + row);
            tableModel.addRow(row);
        }

    }

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
        dm.setRowCount(0);
    }
    private void refreshTable(){
        cleanTable();
        System.out.println("1++++++++++++++++++++" + projectsDtoService.countOfDevelopers());
        add_row(convertFromLinked(projectsDtoService.countOfDevelopers()));
    }

    public static Integer getProjectId(){
        return projectEditId;
    }
}
