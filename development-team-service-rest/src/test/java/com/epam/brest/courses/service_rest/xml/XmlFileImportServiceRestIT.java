package com.epam.brest.courses.service_rest.xml;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.xml.XmlFileImportService;
import com.epam.brest.courses.service_rest.testConfig.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(classes={TestConfig.class} )
@ExtendWith(SpringExtension.class)
class XmlFileImportServiceRestIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlFileImportServiceRest.class);

    public static final String PROJECTS_URL = "http://localhost:8088";

    private final String PROJECTS_ZIP_FILE = "src/test/resources/zip/zip_pro/projects_zip.zip";

    private String DEVELOPERS_ZIP_FILE = "src/test/resources/zip/zip_dev/developers_zip.zip";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private XmlFileImportService xmlFileImportService;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        xmlFileImportService = new XmlFileImportServiceRest(PROJECTS_URL , restTemplate);
    }

    @Test
    void shouldParseProjectsXMLFile() throws URISyntaxException, IOException {

        LOGGER.debug("shouldParseProjectsXMLFile()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PROJECTS_URL + "/projects/import/xml")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(createProjects(0), createProjects(1))))
                );

        File file = new File(PROJECTS_ZIP_FILE);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        // when
        List<Projects> projectsList = xmlFileImportService.parseProjectsXMLFile(multipartFile);

        // then
        mockServer.verify();
        assertNotNull(projectsList);
        assertTrue(projectsList.size() > 0);
    }

    @Test
    void shouldParseDevelopersXMLFile() throws URISyntaxException, IOException {
        LOGGER.debug("shouldParseProjectsXMLFile()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PROJECTS_URL + "/developers/import/xml")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(createDevelopers(0), createDevelopers(1))))
                );

        File file = new File(DEVELOPERS_ZIP_FILE);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        // when
        List<Developers> developersList = xmlFileImportService.parseDevelopersXMLFile(multipartFile);

        // then
        mockServer.verify();
        assertNotNull(developersList);
        assertTrue(developersList.size() > 0);
    }

    private Projects createProjects(int index){

        Projects projects = new Projects();
        projects.setProjectId(index);
        projects.setDescription("description"+index);
        projects.setDateAdded(LocalDate.now());
        return projects;
    }
    private Developers createDevelopers(int index){

        Developers developer = new Developers();
        developer.setDeveloperId(index);
        developer.setFirstName("F" + index);
        developer.setLastName("L" + index);
        return developer;
    }
}