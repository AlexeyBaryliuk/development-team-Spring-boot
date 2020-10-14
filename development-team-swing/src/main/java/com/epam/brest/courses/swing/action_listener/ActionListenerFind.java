package com.epam.brest.courses.swing.action_listener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerFind implements ActionListener {

    JTextField from;
    JTextField to;

    public ActionListenerFind(JTextField from, JTextField to){
        this.from = from;
        this.to = to;
    }
    @Override
    public void actionPerformed(ActionEvent a) {

        System.out.println("Field from: " + from.getText() + " to: " + to.getText());

    }
}
