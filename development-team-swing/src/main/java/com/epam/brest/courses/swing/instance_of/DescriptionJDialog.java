package com.epam.brest.courses.swing.instance_of;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DescriptionJDialog extends JDialog {



    public DescriptionJDialog(String title, boolean modal) {

        super(new JDialog(), title, modal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 100);
        JPanel buttonPane = new JPanel(new FlowLayout());

        JButton button = new JButton("Close");
        button.setBackground(Color.GRAY);
        buttonPane.add(button);

        add(buttonPane, BorderLayout.PAGE_END);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
            }
        });
    }
}
