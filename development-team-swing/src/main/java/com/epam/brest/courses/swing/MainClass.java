package com.epam.brest.courses.swing;


import com.epam.brest.courses.swing.panel.DevelopmentTeam;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class MainClass {
    public static void main(String[] args) {



        EventQueue.invokeLater(() -> {

            ApplicationContext ctx = new SpringApplicationBuilder(MainClass.class)
                    .headless(false).run(args);
            JFrame frame = ctx.getBean(DevelopmentTeam.class);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.setSize(600,400);
        });
    }
}
