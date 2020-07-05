package com.epam.brest.courses.service.xml;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class XmlFileExportServiceImpl implements XmlFileExportService{

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlFileExportServiceImpl.class);

    @Value("${pro.path_to_folder}")
    private String path_to_folder;

    @Value("${pro.zip_file}")
    private String projectsZipFile;

    @Autowired
    private CheckFolder checkFolder;

    @Autowired
    private XmlArchiveService xmlArchiveService;

    @Override
    public void exportProjectsToXml(List<Projects> projectsList) throws IOException, XMLStreamException {

        LOGGER.debug("List of projects({})", projectsList);

        String source_dir = checkFolder.checkFolder(path_to_folder);

        File file = new File(path_to_folder);

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
        }
        try {
            xmlArchiveService.zip(source_dir, projectsZipFile);
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
        writer.writeAttribute("ProjectId", project.getProjectId().toString());

        writer.writeStartElement("description");
        writer.writeCharacters(project.getDescription());
        writer.writeEndElement();

        writer.writeStartElement("dateAdded");
        System.out.println(project.getDateAdded().toString());
        writer.writeCharacters(project.getDateAdded().toString());
        writer.writeEndElement();

        writer.writeEndElement();
    }

    public static void main(String[] args) throws IOException, XMLStreamException {
        List<Projects> projects = new ArrayList<>();

        Projects projects1 = new Projects();
        projects1.setProjectId(1);
        projects1.setDescription("1");
        projects.add(projects1);

        Projects projects2 = new Projects();
        projects2.setProjectId(2);
        projects2.setDescription("2");
        projects.add(projects2);

        XmlFileExportServiceImpl xmlFileExportService = new XmlFileExportServiceImpl();
        xmlFileExportService.exportProjectsToXml(projects);
    }
    @Override
    public void exportDevelopersToXml(List<Developers> developersList) {
    }
}
