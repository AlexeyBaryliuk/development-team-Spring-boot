package com.epam.brest.courses.web_app.validators;

import com.epam.brest.courses.model.Projects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.thymeleaf.util.StringUtils;

import static com.epam.brest.courses.model.constants.ProjectConstants.PROJECT_DESCRIPTION_SIZE;
import static org.junit.jupiter.api.Assertions.*;

class ProjectsValidatorTest {

    private Projects project;

    private ProjectsValidator projectsValidator = new ProjectsValidator();

    private BindingResult result;

    @BeforeEach
    void setup(){
        project = Mockito.mock(Projects.class);
        result = new BeanPropertyBindingResult(project, "projects");
    }

    @Test
    void shouldRejectNullDescription() {

        //given
        Mockito.when(project.getDescription()).thenReturn(null);

        //when
        projectsValidator.validate(project,result);

        //then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectEmptyDescription() {

        //given
        Mockito.when(project.getDescription()).thenReturn("");

        //when
        projectsValidator.validate(project,result);

        //then
        assertTrue(result.hasErrors());

    }

    @Test
    void shouldRejectDescription() {

        //given
        String filled = StringUtils.repeat("*", PROJECT_DESCRIPTION_SIZE + 1);
        Mockito.when(project.getDescription()).thenReturn(filled);

        //when
        projectsValidator.validate(project,result);

        //then
        assertTrue(result.hasErrors());

    }

    @Test
    void shouldValidateDescription() {

        //given
        String description = StringUtils.repeat("*", PROJECT_DESCRIPTION_SIZE);
        Mockito.when(project.getDescription()).thenReturn(description);

        //when
        projectsValidator.validate(project,result);

        //then
        assertFalse(result.hasErrors());

    }
}