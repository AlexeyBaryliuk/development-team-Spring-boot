package com.epam.brest.courses.service.xml;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@SuppressFBWarnings(value = {"OBL_UNSATISFIED_OBLIGATION"})
public class XmlFileImportServiceImpl implements XmlFileImportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlFileImportServiceImpl.class);

   @Autowired
   private ArchiveService archiveService;

    @Override
    public List<Projects> parseProjectsXMLFile(MultipartFile multipartFile) {

        LOGGER.debug("parseProjectsXMLFile({})", multipartFile);

        String fileName = null;

        try {
            fileName = archiveService.unzip(multipartFile);
        } catch (Exception e) {
            LOGGER.debug("File wasn't created.");
        }

        List<Projects> projectsList = new ArrayList<>();
            Projects project = null;
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            try {

                XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));

                while (reader.hasNext()) {

                    XMLEvent xmlEvent = reader.nextEvent();
                    if (xmlEvent.isStartElement()) {
                        StartElement startElement = xmlEvent.asStartElement();
                        if (startElement.getName().getLocalPart().equals("project")) {
                            project = new Projects();

                            Attribute idAttr = startElement.getAttributeByName(new QName("projectId"));
                            if (idAttr != null) {
                                project.setProjectId(Integer.parseInt(idAttr.getValue()));
                            }
                        } else if (startElement.getName().getLocalPart().equals("description")) {
                            xmlEvent = reader.nextEvent();
                            project.setDescription(xmlEvent.asCharacters().getData());
                        } else if (startElement.getName().getLocalPart().equals("dateAdded")) {
                            xmlEvent = reader.nextEvent();
                            String dateAdded = xmlEvent.asCharacters().getData();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            formatter = formatter.withLocale( Locale.ENGLISH );
                            LocalDate date = LocalDate.parse(dateAdded, formatter);
                            project.setDateAdded(date);

                        }
                    }

                    if (xmlEvent.isEndElement()) {
                        EndElement endElement = xmlEvent.asEndElement();
                        if (endElement.getName().getLocalPart().equals("project")) {
                            projectsList.add(project);
                        }
                    }
                }

            } catch (XMLStreamException | IOException exc) {
                exc.printStackTrace();
            }
            return projectsList;
        }

    @Override
    public List<Developers> parseDevelopersXMLFile(MultipartFile multipartFile) {

        LOGGER.debug("parseDevelopersXMLFile({})", multipartFile);

        String fileName = null;

        try {
            fileName = archiveService.unzip(multipartFile);
        } catch (Exception e) {
            LOGGER.debug("File wasn't created.");
        }

        List<Developers> developersList = new ArrayList<>();
        Developers developer = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {

            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));

            while (reader.hasNext()) {

                XMLEvent xmlEvent = reader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals("developer")) {
                        developer = new Developers();

                        Attribute idAttr = startElement.getAttributeByName(new QName("developerId"));
                        if (idAttr != null) {
                            developer.setDeveloperId(Integer.parseInt(idAttr.getValue()));
                        }
                    } else if (startElement.getName().getLocalPart().equals("firstname")) {
                        xmlEvent = reader.nextEvent();
                        developer.setFirstName(xmlEvent.asCharacters().getData());
                    } else if (startElement.getName().getLocalPart().equals("lastname")) {
                        xmlEvent = reader.nextEvent();
                        developer.setLastName(xmlEvent.asCharacters().getData());
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("developer")) {
                        developersList.add(developer);
                    }
                }
            }

        } catch (XMLStreamException | IOException exc) {
            exc.printStackTrace();
        }
        return developersList;
    }

}
