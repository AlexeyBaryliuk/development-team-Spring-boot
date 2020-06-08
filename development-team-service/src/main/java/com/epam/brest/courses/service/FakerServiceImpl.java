package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.DevelopersDao;
import com.epam.brest.courses.dao.ProjectsDao;
import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@SuppressFBWarnings(value = "DLS_DEAD_LOCAL_STORE")
public class FakerServiceImpl implements FakerService {

    Locale localLanguage  = new Locale("en");

    Integer numberOfChanges = 1;

    @Autowired
    private DevelopersDao developersDao;

    @Autowired
    private ProjectsDao projectsDao;

    @Override
    public void changeProjectsTestData(String locale, Integer numberOfChanges) {

        localLanguage = checkLocale(locale);

        Integer countOfProjects = projectsDao.countOfRow();

        if (numberOfChanges != null) {
            this.numberOfChanges = countOfProjects * numberOfChanges - countOfProjects;

            for (int i = 0; i < this.numberOfChanges; i++) {

                Projects project = new Projects();
                project.setDateAdded(createFakeDate(localLanguage));
                project.setDescription(creatFakeText(localLanguage));
                projectsDao.create(project);
            }
        }
    }

    @Override
    public void changeDevelopersTestsData(String locale, Integer numberOfChanges) {

        localLanguage = checkLocale(locale);

        Faker faker = new Faker(localLanguage);

        Integer countOfDevelopers = developersDao.countOfRow();

        if (numberOfChanges != null) {
            this.numberOfChanges = countOfDevelopers * numberOfChanges - countOfDevelopers;

            for (int i = 0; i < this.numberOfChanges; i++) {
                Developers developer = new Developers();
                developer.setFirstName(faker.name().firstName());
                developer.setLastName(faker.name().lastName());
                developersDao.create(developer);
            }
        }
    }

    @Override
    public List<String> findAllFakerLocale() {

        List<String> list = new ArrayList<>();

        String filename="src/main/resources/localeForFaker.txt";
        Path pathToFile = Paths.get(filename);
        try {
           list  = Files.readAllLines(pathToFile.toAbsolutePath(), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Locale checkLocale(String locale){

        if (locale != null && !localLanguage.getLanguage().equals(locale)){
            localLanguage = new Locale(locale);
        }
        return localLanguage;
    }

    public LocalDate createFakeDate(Locale locale){

        Faker faker = new Faker(locale);

        int year =faker.number().numberBetween(1950,2020);
        int month =faker.number().numberBetween(1,12);
        int day =faker.number().numberBetween(1,30);
        LocalDate fakeLocalDate = LocalDate.of(year,month,day);

        return fakeLocalDate;
    }

    public String creatFakeText(Locale locale){

        FakeValuesService fakeValuesService = new FakeValuesService(
                locale, new RandomService());
        String fakeText = fakeValuesService.regexify("[a-z1-9]{40}");

        return fakeText;
    }
}
