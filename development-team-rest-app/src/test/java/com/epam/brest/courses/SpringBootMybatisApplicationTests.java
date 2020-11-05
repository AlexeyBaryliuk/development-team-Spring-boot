package com.epam.brest.courses;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.myBatis.ProjectsMyBatis;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMybatisApplicationTests {

    @Autowired
    private ProjectsMyBatis projectsMyBatis;

    @Test
    public void contextLoads() {
    }

    @Test
    public void mapperIsLoad(){
        List<Projects> projectsList = projectsMyBatis.findAll();
        Assert.assertNotNull(projectsList);
    }
}
