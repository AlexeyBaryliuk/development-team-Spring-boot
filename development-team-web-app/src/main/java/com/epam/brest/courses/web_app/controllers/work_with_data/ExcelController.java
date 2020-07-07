package com.epam.brest.courses.web_app.controllers.work_with_data;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.service.ProjectsService;
import com.epam.brest.courses.service.excel.ExcelFileExportService;
import com.epam.brest.courses.service.excel.ExcelFileImportService;
import com.epam.brest.courses.web_app.controllers.ProjectsController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ExcelController.
 */
@Controller
@RequestMapping
@SuppressFBWarnings(value = "SIC_INNER_SHOULD_BE_STATIC_ANON")
public class ExcelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    private ExcelFileExportService exportService;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private DevelopersService developersService;

    @Autowired
    private final ExcelFileImportService excelFileImportServiceRest;

    public ExcelController( ExcelFileImportService excelFileImportServiceRest) {
        this.excelFileImportServiceRest = excelFileImportServiceRest;
    }

    @Autowired
    private ProjectsController projectsController;

    @PostMapping("/projects/import")
    public String projectsImportFile( @RequestParam("file") MultipartFile file){

        LOGGER.debug("projectsImportFile({})", file.getOriginalFilename());

        excelFileImportServiceRest.saveProjectsDataFromUploadFile(file);
        return "redirect:/projects";
    }

    @PostMapping("/developers/import")
    public String developersImportFile(@RequestParam("file") MultipartFile file){

        LOGGER.debug("developersImportFile({})", file.getOriginalFilename());

        excelFileImportServiceRest.saveDevelopersDataFromUploadFile(file);
        return "redirect:/developers";
    }

    @GetMapping("/projects/export/excel")
    public void projectsDownload(HttpServletResponse response) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        List<ProjectsDto> projectsDtoList = projectsController.getProjectsDtoList();

        List<Projects> projectsList = new ArrayList<>();

        List<ProjectsDto> projectDtoListMapper = mapper.convertValue(
                projectsDtoList,
                new TypeReference<List<ProjectsDto>>(){}
        );

        for (int i = 0; i < projectsDtoList.size(); i++) {

            Integer projectsDtoId = projectDtoListMapper.get(i).getProjectId();

            Optional<Projects> project = projectsService.findByProjectId(projectsDtoId);
            project.ifPresent(projectsList::add);

        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename = projects.xlsx");
        ByteArrayInputStream stream = exportService.exportProjectsToExcel(projectsList);
        IOUtils.copy(stream, response.getOutputStream());

    }
    @GetMapping("/developers/export/excel")
    public void developersDownload(HttpServletResponse response) throws IOException {


        LOGGER.debug("developersDownload()");

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename = developers.xlsx");
        ByteArrayInputStream stream = exportService.exportDevelopersToExcel(developersService.findAll());
        IOUtils.copy(stream, response.getOutputStream());

    }


}
