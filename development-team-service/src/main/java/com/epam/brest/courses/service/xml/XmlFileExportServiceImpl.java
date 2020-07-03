package com.epam.brest.courses.service.xml;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class XmlFileExportServiceImpl implements XmlFileExportService{

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlFileExportServiceImpl.class);

    private String PATH_TO_PROJECTS = "xml/projects.xml";

    @Override
    public void exportProjectsToXml(List<Projects> projectsList) throws IOException, XMLStreamException {

        LOGGER.debug("List of projects({})", projectsList);


        File file = new File(PATH_TO_PROJECTS);

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

    @Override
    public void exportDevelopersToXml(List<Developers> developersList) {
    }
}
