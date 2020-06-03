package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.DevelopersDao;
import com.epam.brest.courses.dao.ProjectsDao;
import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Locale;

public class FakerServiceImpl implements FakerService {

    @Autowired
    private DevelopersDao developersDao;

    @Autowired
    private ProjectsDao projectsDao;

    @Override
    public void changeData(String local, Integer numberOfChanges) {

        String localLanguage = "en";

        if (local != null && !localLanguage.equals(local)){
            localLanguage = local;
        }

        Faker faker = new Faker(new Locale(localLanguage));

        Integer countOfDevelopers = developersDao.countOfRow();
        Integer countOfProjects = projectsDao.countOfRow();

        Integer numberOfDevelopersChanges = countOfDevelopers * numberOfChanges - countOfDevelopers;

        for (int i = 0; i < numberOfDevelopersChanges ; i++) {
            Developers developer = new Developers();
            developer.setFirstName(faker.name().firstName());
            developer.setLastName(faker.name().lastName());
            developersDao.create(developer);
        }

        Integer  numberOfProjectsChanges = countOfProjects*numberOfChanges-countOfProjects;

                for (int i = 0; i < numberOfProjectsChanges ; i++) {

                    Projects project = new Projects();
                    project.setDateAdded(createFakeDate(localLanguage));
                    project.setDescription(creatFakeText(localLanguage));
                    projectsDao.create(project);
                }
            }

    public LocalDate createFakeDate(String local){

        Faker faker = new Faker(new Locale(local));

        int year =faker.number().numberBetween(1950,2020);
        int month =faker.number().numberBetween(1,12);
        int day =faker.number().numberBetween(1,30);

        LocalDate fakeLocalDate = LocalDate.of(year,month,day);

        return fakeLocalDate;
    }

    public String creatFakeText(String local){

        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale(local), new RandomService());

        String fakeText = fakeValuesService.regexify("[a-z1-9]{40}");

        return fakeText;
    }
}
