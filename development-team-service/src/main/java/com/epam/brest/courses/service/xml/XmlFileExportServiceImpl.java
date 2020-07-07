package com.epam.brest.courses.service.xml;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;

@Service
@PropertySource("classpath:xml.properties")
public class XmlFileExportServiceImpl implements XmlFileExportService{

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlFileExportServiceImpl.class);

    @Value("${pro.path_to_folder}")
    private String pathToProjectsFolder;

    @Value("${pro.zip_file}")
    private String projectsZipFile;

    @Value("${dev.path_to_folder}")
    private String pathToDevelopersFolder;

    @Value("${dev.zip_file}")
    private String developersZipFile;

    @Autowired
    private CheckFolder checkFolder;

    @Autowired
    private ArchiveService archiveService;

    @Override
    public void exportProjectsToXml(List<Projects> projectsList) throws IOException {

        LOGGER.debug("List of projects({})", projectsList);

        String source_dir = checkFolder.checkFolder(pathToProjectsFolder);

        File file = new File(pathToProjectsFolder);

        try (OutputStream os = Files.newOutputStream(file.toPath())) {
            XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
            XMLStreamWriter writer = null;
            try {
                writer = outputFactory.createXMLStreamWriter(os, "utf-8");
                writeProjectsElem(writer, projectsList);
            } finally {
                if (writer != null)
                    writer.close();
            }
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }

        checkFolder.checkFolder(projectsZipFile);

        try {
            archiveService.zip(source_dir, projectsZipFile);
        } catch (Exception e) {
            LOGGER.debug("Archive was not created");
        }
    }

    private void writeProjectsElem(XMLStreamWriter streamWriter, List<Projects> projects) throws XMLStreamException {

        IndentingXMLStreamWriter writer = null;
        writer = new IndentingXMLStreamWriter(streamWriter);

        writer.writeStartDocument("utf-8", "1.0");
//        writer.writeComment("Describes list of projects");

        writer.writeStartElement("projects");
        for (Projects project : projects)
            writeProjectElem(writer, project);
        writer.writeEndElement();

        writer.writeEndDocument();
    }

    private void writeProjectElem(XMLStreamWriter writer, Projects project) throws XMLStreamException {
        writer.writeStartElement("project");
        writer.writeAttribute("projectId", project.getProjectId().toString());

        writer.writeStartElement("description");
        writer.writeCharacters(project.getDescription());
        LOGGER.debug("++++++++++++++++++++++ = {}", project.getDescription() );
        writer.writeEndElement();

        writer.writeStartElement("dateAdded");
        writer.writeCharacters(project.getDateAdded().toString());
        writer.writeEndElement();

        writer.writeEndElement();
    }

    @Override
    public void exportDevelopersToXml(List<Developers> developersList) {

        LOGGER.debug("List of developers({})", developersList);

        String source_dir = checkFolder.checkFolder(pathToDevelopersFolder);

        File file = new File(pathToDevelopersFolder);

        try (OutputStream os = Files.newOutputStream(file.toPath())) {
            XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
            XMLStreamWriter writer = null;
            try {
                writer = outputFactory.createXMLStreamWriter(os, "utf-8");
                writeDevelopersElem(writer, developersList);
            } finally {
                if (writer != null)
                    writer.close();
            }
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
        checkFolder.checkFolder(developersZipFile);

        try {
            archiveService.zip(source_dir, developersZipFile);
        } catch (Exception e) {
            LOGGER.debug("Archive was not created");
        }
    }

    private void writeDevelopersElem(XMLStreamWriter streamWriter, List<Developers> developers) throws XMLStreamException {

        IndentingXMLStreamWriter writer = null;
        writer = new IndentingXMLStreamWriter(streamWriter);

        writer.writeStartDocument("utf-8", "1.0");
//        writer.writeComment("Describes list of projects");

        writer.writeStartElement("developers");
        for (Developers developer : developers)
            writeDeveloperElem(writer, developer);
        writer.writeEndElement();

        writer.writeEndDocument();
    }

    private void writeDeveloperElem(XMLStreamWriter writer, Developers developer) throws XMLStreamException {
        writer.writeStartElement("developer");
        writer.writeAttribute("developerId", developer.getDeveloperId().toString());

        writer.writeStartElement("firstname");
        writer.writeCharacters(developer.getFirstName());
        writer.writeEndElement();

        writer.writeStartElement("lastname");
        writer.writeCharacters(developer.getLastName());
        writer.writeEndElement();

        writer.writeEndElement();
    }
}
