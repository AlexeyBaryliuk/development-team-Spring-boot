package com.epam.brest.courses.service.excel;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface ExcelFileExportService {

    ByteArrayInputStream exportProjectsToExcel(List<Projects> projectsList) throws IOException;

    ByteArrayInputStream exportDevelopersToExcel(List<Developers> developersList);
}
