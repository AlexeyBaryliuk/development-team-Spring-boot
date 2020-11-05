package com.epam.brest.courses.myBatis;


import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ProjectsMapperTest extends CommonMyBatisTest{

    @Autowired
    private ProjectsMyBatis projectsMyBatis;

    @Before
    public void createTestDataBase(){

       Projects project = createProject();
        projectsMyBatis.create(project);
    }

    @After
    public void cleanDataBase(){
        projectsMyBatis.deleteAllProject();
    }


    @Test
    public void findAllProjects() {
        List<Projects> projectsList = projectsMyBatis.findAll();
        Assertions.assertNotNull(projectsList);
    }

    @Test
    public void updateProject(){

            Projects projectBeforeUpdate = projectsMyBatis.findAll().get(0);

            Integer projectId = projectBeforeUpdate.getProjectId();
            String oldDescription = projectBeforeUpdate.getDescription();
            projectBeforeUpdate.setDescription("NewDescription");

            projectsMyBatis.update(projectBeforeUpdate);

        Projects projectAfterUpdate = projectsMyBatis.findByProjectId(projectId).get();

            assertFalse(oldDescription.equals(projectAfterUpdate.getDescription()));

    }


    private Projects createProject(){

        Projects project = new Projects();

            project.setDescription("Test");
            project.setDateAdded(LocalDate.now());

        return project;
    }

}
