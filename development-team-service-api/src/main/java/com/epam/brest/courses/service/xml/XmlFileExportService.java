package com.epam.brest.courses.service.xml;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

/**
 * XmlFileExportService1 interface.
 */
public interface XmlFileExportService {

    void exportProjectsToXml(List<Projects> projectsList) throws IOException, XMLStreamException;

    void exportDevelopersToXml(List<Developers> developersList);
}
