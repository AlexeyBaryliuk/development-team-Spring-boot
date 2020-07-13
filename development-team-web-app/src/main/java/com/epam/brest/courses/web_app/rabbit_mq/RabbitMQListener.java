package com.epam.brest.courses.web_app.rabbit_mq;

import com.epam.brest.courses.web_app.controllers.ProjectsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class RabbitMQListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsController.class);

    private String message = "The project has no changes";

    @RabbitListener(queues = "projects_changes")
    public void message(String message) throws InterruptedException {

        this.message = message;
        LOGGER.debug("Received from projects_changes: {}", message);
    }

    public String getMessage(){
        return message;
    }

}
