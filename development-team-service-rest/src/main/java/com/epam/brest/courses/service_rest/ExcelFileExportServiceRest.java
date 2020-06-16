package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.excel.ExcelFileExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.List;

public class ExcelFileExportServiceRest implements ExcelFileExportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelFileExportServiceRest.class);

    @Override
    public ByteArrayInputStream exportProjectsToExcel(List<Projects> projectsList) {
        LOGGER.debug("exportProjectsToExcel({})", projectsList);
        return null;
    }

    @Override
    public ByteArrayInputStream exportDevelopersToExcel(List<Developers> developersList) {
        return null;
    }
}
