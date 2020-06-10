package com.epam.brest.courses.rest_app.controllers;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.service.DevelopersService;
import com.epam.brest.courses.service.ExcelWriteService;
import com.epam.brest.courses.service.ProjectsService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping
@SuppressFBWarnings(value = {"RV_RETURN_VALUE_IGNORED_BAD_PRACTICE"})
public class ExcelController {

    Logger LOGGER = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    private ServletContext context;

    @Autowired
    private final ExcelWriteService excelWriteService;

    @Autowired
    private final ProjectsService projectsService;

    @Autowired
    private final DevelopersService developersService;

    public ExcelController(ExcelWriteService excelWriteService
                            , ProjectsService projectsService
                            , DevelopersService developersService) {
        this.excelWriteService = excelWriteService;
        this.projectsService = projectsService;
        this.developersService = developersService;
    }

    @GetMapping("/createProjectsExcel")
    public void createProjectsExcel(HttpServletRequest request, HttpServletResponse response){

        List<Projects> projects = projectsService.findAll();

        boolean isFlag = excelWriteService.createProjectExcel(projects, context,request, response);

        if (isFlag){
            String fullPath = request.getServletContext().getRealPath("/resources/" + "projects" + ".xls");
            fileDownload(fullPath,response,"projects.xls");
        }
    }

    @GetMapping("/createDevelopersExcel")
    public void createDevelopersExcel(HttpServletRequest request, HttpServletResponse response){

        List<Developers> developers = developersService.findAll();

        boolean isFlag = excelWriteService.createDeveloperExcel(developers, context,request, response);

        if (isFlag){
            String fullPath = request.getServletContext().getRealPath("/resources/" + "developers" + ".xls");
            fileDownload(fullPath,response,"developers.xls");
        }
    }

    private void fileDownload(String fullPath, HttpServletResponse response, String filename) {

        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        File file = new File(fullPath);
        final int BUFFER_SIZE = 4096;

        if (file.exists()){
            try {
                inputStream = new FileInputStream(file);
                String mimeTipe = context.getMimeType(fullPath);
                response.setContentType(mimeTipe);
                response.setHeader("content-disposition", "attachment; filename = " + filename );
                outputStream = response.getOutputStream();

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1){

                    outputStream.write(buffer, 0, bytesRead);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    inputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                file.delete();
            }
        }
    }
}
