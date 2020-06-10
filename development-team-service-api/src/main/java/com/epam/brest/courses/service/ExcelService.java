package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Read and write data from excel.
 *
 */
public interface ExcelService {


    boolean createProjectExcel(List<Projects> projects
            , ServletContext context
            , HttpServletRequest httpServletRequest
            , HttpServletResponse response );

    boolean createDeveloperExcel(List<Developers> developers
            , ServletContext context
            , HttpServletRequest request
            , HttpServletResponse response);
}
