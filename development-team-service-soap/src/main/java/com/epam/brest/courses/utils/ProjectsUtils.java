package com.epam.brest.courses.utils;

import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.wsdl.ProjectInfo;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.time.LocalDate;

public class ProjectsUtils {


    public static ProjectInfo convertProjectsToProjectInfo(Projects project) throws DatatypeConfigurationException {

        ProjectInfo projectInfo = new ProjectInfo();

        projectInfo.setProjectId(project.getProjectId());
        projectInfo.setDescription(project.getDescription());
        projectInfo.setDateAdded(DatatypeFactory
                .newInstance()
                .newXMLGregorianCalendar(project.getDateAdded().toString()));

        return projectInfo;
    }

    public static Projects convertProjectInfoToProjects(ProjectInfo projectInfo) {

        Projects project = new Projects();

        project.setProjectId(projectInfo.getProjectId());
        project.setDescription(projectInfo.getDescription());
        project.setDateAdded(LocalDate.parse(projectInfo.getDateAdded().toXMLFormat()));

        return project;
    }
}

