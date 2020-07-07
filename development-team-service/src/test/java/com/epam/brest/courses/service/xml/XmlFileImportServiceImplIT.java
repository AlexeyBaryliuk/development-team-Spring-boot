package com.epam.brest.courses.service.xml;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.testConfig.TestConfig;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes={Projects.class,Developers.class, TestConfig.class} )
@ExtendWith(SpringExtension.class)
@TestPropertySource({"classpath:sql-development-team.properties", "classpath:xml.properties"})
class XmlFileImportServiceImplIT {

    @Value("${pro.zip_file}")
    private String projects_zip_file;

    @Value("${dev.zip_file}")
    private String developers_zip_file;

    @Autowired
    private XmlFileExportServiceImpl xmlFileExportService;

    @Autowired
    private XmlFileImportService xmlFileImportService;

    int id = 1;



    @Test
    void parseProjectsXMLFile() throws IOException {

        List<Projects> projectsList = new ArrayList<>();
        Projects project = createProjects();
        projectsList.add(project);
        Projects project1 = createProjects();
        projectsList.add(project1);
        Projects project2 = createProjects();
        projectsList.add(project2);

        xmlFileExportService.exportProjectsToXml(projectsList);

        File file = new File(projects_zip_file);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));

        List<Projects> projectsListAfter = xmlFileImportService.parseProjectsXMLFile(multipartFile);

        assertNotNull(projectsListAfter);
        assertEquals(projectsListAfter.size(), projectsList.size() );
        assertEquals(project.getProjectId(), projectsListAfter.get(0).getProjectId());
    }

    @Test
    void parseDevelopersXMLFile() throws IOException {
        List<Developers> developersList = new ArrayList<>();
        Developers developer = createDevelopers();
        developersList.add(developer);
        Developers developer1 = createDevelopers();
        developersList.add(developer1);
        Developers developer2 = createDevelopers();
        developersList.add(developer2);

        xmlFileExportService.exportDevelopersToXml(developersList);

        File file = new File(developers_zip_file);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));

        List<Developers> developersListAfter = xmlFileImportService.parseDevelopersXMLFile(multipartFile);

        assertNotNull(developersListAfter);
        assertEquals(developersListAfter.size(), developersList.size() );
        assertEquals(developer.getDeveloperId(), developersListAfter.get(0).getDeveloperId());
    }

    public Projects createProjects(){

        Projects project = new Projects();
        project.setDescription("Test");
        project.setProjectId(id);
        id++;
        return project;
    }

    public Developers createDevelopers(){

        Developers developer = new Developers();
        developer.setFirstName("Test" );
        developer.setLastName("Developer");
        developer.setDeveloperId(id);
        id++;

        return developer;
    }
}