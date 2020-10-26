package com.epam.brest.courses.swing.panel.developers_panels;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.swing.editor.ButtonEditEditor;
import com.epam.brest.courses.swing.instance_of.DevelopersTableModel;
import com.epam.brest.courses.swing.instance_of.ProjectsTableModel;
import com.epam.brest.courses.swing.panel.projects_panels.ProjectsCards;
import com.epam.brest.courses.swing.panel.projects_panels.ProjectsPanel;
import com.epam.brest.courses.swing.renderer.ButtonEditRenderer;
import com.epam.brest.courses.swing.renderer.RowRenderer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class DevelopersPanel extends JPanel {

    private static int developerEditId = 0;
    private JButton edit;
    private JButton delete;
    private JButton add;
    private JTable table;
    private JPanel northPanel;
    private Box contents;
    private JPanel header;
    private List<Developers> developersList ;
    private  DevelopersCards parent;
    private ObjectMapper mapper = new ObjectMapper();

    private final DevelopersService developersService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DevelopersPanel.class);

    public DevelopersPanel(DevelopersService developersService) {
        this.developersService = developersService;

        northPanel = new JPanel();
        header = new JPanel();
        delete = new JButton();

        add = new JButton("Add");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                parent = (DevelopersCards) getParent();

                CardLayout layout = (CardLayout)(parent.getLayout());
                layout.show(parent, DevelopersCards.ADD_DEVELOPERS);

            }
        });

        edit = new JButton();
        edit.addActionListener(actionEvent -> {
            parent = (DevelopersCards) getParent();

            int row = table.getSelectedRow();
            developerEditId = (int)table.getModel().getValueAt(row, 0);

            if (table.isEditing())
                table.getCellEditor().stopCellEditing();

            CardLayout layout = (CardLayout)(parent.getLayout());
            layout.show(parent, DevelopersCards.EDIT_DEVELOPERS);

        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent evt) {

                refreshTable();
            }
        });

        DevelopersTableModel model = new DevelopersTableModel();

        table = new JTable();
        table.setModel(model);

        table.getColumn("edit").setCellRenderer(new ButtonEditRenderer());
        table.getColumn("edit").setCellEditor(new ButtonEditEditor(new JCheckBox(), edit));

        table.getColumn("delete").setCellRenderer(new ButtonEditRenderer());
        table.getColumn("delete").setCellEditor(new ButtonEditEditor(new JCheckBox(),delete));


        table.getColumn("developerId").setCellRenderer(new RowRenderer());
        table.getColumn("FirstName").setCellRenderer(new RowRenderer());
        table.getColumn("LastName").setCellRenderer(new RowRenderer());

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultTableModel dm = (DefaultTableModel)table.getModel();

                int row = table.getSelectedRow();
                int developerId = (int)table.getModel().getValueAt(row, 0);
                try {
                    developersService.deleteByDeveloperId(developerId);

                    if (table.isEditing())
                        table.getCellEditor().stopCellEditing();

                    ((DefaultTableModel)table.getModel()).removeRow(row);

                }
                catch (Exception ex){
                    LOGGER.debug("The row number {} wasn't removed.", row);
                }
            }
        });
        contents = new Box(BoxLayout.Y_AXIS);
        contents.add(new JScrollPane(table));

        setLayout(new BorderLayout());
        northPanel.setLayout(new BorderLayout());

        header.setLayout(new FlowLayout(FlowLayout.RIGHT));
        header.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        header.add(add);
        northPanel.add(header, BorderLayout.NORTH);

        add(contents, BorderLayout.CENTER);
        add(northPanel,BorderLayout.NORTH);
        add_row(this.developersService.findAll());
    }

    public List<Developers> convertFromLinked(List<Developers> developersList){

        List<Developers> developers = mapper.convertValue(
                developersList,
                new TypeReference<List<Developers>>(){}
        );
        return developers;
    }

    public void cleanTable(){

        DefaultTableModel dm = (DefaultTableModel)table.getModel();
        dm.setRowCount(0);
    }

    private void refreshTable() {
        cleanTable();
        System.out.println("1++++++++++++++++++++" + developersService.findAll());
        add_row(convertFromLinked(developersService.findAll()));

    }

    public void add_row(List<Developers> obj) {

        developersList = convertFromLinked(obj);
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

        Object[] row = new Object[5];
        for (int i = 0; i < obj.size(); i++) {

            row[0] = developersList.get(i).getDeveloperId();
            row[1] = developersList.get(i).getFirstName();
            row[2] = developersList.get(i).getLastName();
            row[3] = "edit";
            row[4] = "delete";

            System.out.println("Row++++++" + row);
            tableModel.addRow(row);
        }
    }

    public static Integer getDeveloperId(){
        return developerEditId;
    }
}
