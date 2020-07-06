package com.epam.brest.courses.service.xml;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@PropertySource("classpath:xml.properties")
public class XmlFileImportServiceImpl implements XmlFileImportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlFileImportServiceImpl.class);

    private static final String SLASH_BACK      = "/";

    @Value("${path_to_unzipFolder}")
    private String pathToProjectsUnzipFolder;

    @Autowired
    private CheckFolder checkFolder;


    private String unzip(MultipartFile multipartFile) throws Exception
    {
        File file = convertMultiPartToFile(multipartFile);

        String pathToFile = "";

        ZipFile zipFile = new ZipFile(file);
        Enumeration<?> entries = zipFile.getEntries();
        LOGGER.debug("++++++++++++++++ = {}", entries.hasMoreElements());

        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            LOGGER.debug("********************* = {}" , entry.getSize());
            String entryName = entry.getName();

            if (entryName.endsWith(SLASH_BACK)) {
                LOGGER.debug("Create the directory <{}>", entryName );
                checkFolder.createFolder (entryName);
                continue;
            } else
                checkFolder.checkFolder(entryName, pathToProjectsUnzipFolder);
            LOGGER.debug("Reading the file < {} >" , entryName );

            InputStream fis = zipFile.getInputStream(entry);
            LOGGER.debug("/////////////////fis.available() = {} ", fis.available());

            pathToFile = pathToProjectsUnzipFolder + entryName;
            FileOutputStream fos = new FileOutputStream(pathToFile);

            copyData(fis,fos);

            fis.close();
            fos.close();
        }
        zipFile.close() ;
        LOGGER.debug("Zip file has unzipped!");

        return pathToFile;
    }

    private static File convertMultiPartToFile(MultipartFile file ) throws IOException
    {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        return convFile;
    }

    private static void copyData(InputStream in, OutputStream out) throws Exception {
        int b;
        while ((b = in.read()) > 0) {

            out.write(b);

        }
    }

    @Override
    public List<Projects> parseProjectsXMLFile(MultipartFile multipartFile) {

        String fileName = null;

        try {
            fileName = unzip(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
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

            } catch (XMLStreamException | FileNotFoundException exc) {
                exc.printStackTrace();
            }
            return projectsList;
        }

    @Override
    public List<Developers> parseDevelopersXMLFile(MultipartFile multipartFile) {

        String fileName = null;

        try {
            fileName = unzip(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
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

        } catch (XMLStreamException | FileNotFoundException exc) {
            exc.printStackTrace();
        }
        return developersList;
    }

}
