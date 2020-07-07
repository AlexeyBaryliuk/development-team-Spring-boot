package com.epam.brest.courses.service_rest.xml;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service_rest.testConfig.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(classes={TestConfig.class} )
@ExtendWith(SpringExtension.class)
class XmlFileExportServiceRestIT {


    private static final Logger LOGGER = LoggerFactory.getLogger(XmlFileExportServiceRest.class);

    public static final String PROJECTS_URL = "http://localhost:8088";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private XmlFileExportServiceRest xmlFileExportServiceRest;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        xmlFileExportServiceRest = new XmlFileExportServiceRest(PROJECTS_URL , restTemplate);
    }

    @Test
    void shouldExportProjectsToXml() throws URISyntaxException, IOException, XMLStreamException {

        LOGGER.debug("shouldExportProjectsToXml()");

        int index = 1;
        List<Projects> projectsList = new ArrayList<>();
        projectsList.add(createProjects(index));

        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PROJECTS_URL + "/projects/export/xml")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        // when
        xmlFileExportServiceRest.exportProjectsToXml(projectsList);

        // then
        mockServer.verify();

    }

    @Test
    void shouldExportDevelopersToXml() throws URISyntaxException {

        LOGGER.debug("shouldExportDevelopersToXml()");

        int index = 1;

        List<Developers> developersList = new ArrayList<>();
        developersList.add(createDevelopers(index));

        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PROJECTS_URL + "/developers/export/xml")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        // when
        xmlFileExportServiceRest.exportDevelopersToXml(developersList);

        // then
        mockServer.verify();

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