package com.epam.brest.courses.service.xml;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * XmlFileImportService interface.
 */
public interface XmlFileImportService {

    List<Projects> parseProjectsXMLFile(MultipartFile multipartFile);
    List<Developers> parseDevelopersXMLFile(MultipartFile multipartFile);
}
