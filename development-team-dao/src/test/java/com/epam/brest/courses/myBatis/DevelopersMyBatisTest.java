package com.epam.brest.courses.myBatis;

import com.epam.brest.courses.model.Developers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DevelopersMyBatisTest extends CommonMyBatisTest {

    @Autowired
    private DevelopersMyBatis developersMyBatis;

    @Before
    public void createTestDataBase(){

        Developers developer = createDeveloper();
            developersMyBatis.create(developer);
    }

    @After
    public void cleanDataBase(){
        developersMyBatis.deleteAllDevelopers();
    }

    @Test
    public void shouldFindAllDevelopers() {

        Developers developer = createDeveloper();
        List<Developers> developersListBefor = developersMyBatis.findAll();

        developersMyBatis.create(developer);

        List<Developers> developersListAfter = developersMyBatis.findAll();
        assertTrue(developersListAfter.size() == developersListBefor.size() + 1);


    }

    @Test
    public void shouldFindDeveloperByDeveloperId() {


        Integer developerId = developersMyBatis.findAll()
                                                    .get(0)
                                                        .getDeveloperId();
        String oldName = developersMyBatis.findAll()
                                            .get(0)
                                                .getFirstName();

        String findName = developersMyBatis.findByDeveloperId(developerId)
                                                .get()
                                                    .getFirstName();
        assertEquals(oldName, findName);
        

    }

    @Test
    public void shouldCreateDeveloper() {

        Developers developer = createDeveloper();

        Integer value = developersMyBatis.create(developer);

        assertEquals(1, (int) value);
    }

    @Test
    public void shouldUpdateDeveloper() {

        Developers developerBeforeUpdate = developersMyBatis.findAll().get(0);

            Integer developerId = developerBeforeUpdate.getDeveloperId();
            String oldName = developerBeforeUpdate.getFirstName();
                developerBeforeUpdate.setFirstName("NewName");

        developersMyBatis.update(developerBeforeUpdate);

        Developers developerAfterUpdate = developersMyBatis.findByDeveloperId(developerId).get();

        assertFalse(oldName.equals(developerAfterUpdate.getFirstName()));

    }

    @Test
    public void shouldDeleteDeveloperByDeveloperId() {

        List<Developers> developersListBefore = developersMyBatis.findAll();

        developersMyBatis.deleteByDeveloperId(developersListBefore.get(0).getDeveloperId());

        List<Developers> developersListAfter = developersMyBatis.findAll();
        assertEquals(developersListBefore.size() - 1, developersListAfter.size());
    }

    private Developers createDeveloper(){

        Developers developer = new Developers();
            developer.setFirstName("FirstNameNew");
            developer.setLastName("LastNameNew");

        return developer;
    }
}