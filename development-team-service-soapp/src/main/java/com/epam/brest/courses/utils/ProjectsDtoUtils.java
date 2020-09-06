package com.epam.brest.courses.utils;


import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.wsdl.ProjectDtoInfo;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectsDtoUtils {


    public static LocalDate convertXMLGregorianCalendarToLocalDate
            (XMLGregorianCalendar xmlGregorianCalendar) throws DatatypeConfigurationException {

        return LocalDate.parse(xmlGregorianCalendar.toXMLFormat());
    }

    public static ProjectsDto convertProjectsDtoInfoToProjectDto(ProjectDtoInfo projectDtoInfo) throws DatatypeConfigurationException {

        ProjectsDto projectsDto = new ProjectsDto();

        projectsDto.setProjectId(projectDtoInfo.getProjectId());
        projectsDto.setDescription(projectDtoInfo.getDescription());
        projectsDto.setDateAdded(convertXMLGregorianCalendarToLocalDate(projectDtoInfo.getDateAdded()));
        projectsDto.setCountOfDevelopers(projectDtoInfo.getCountOfDevelopers());

        return projectsDto;
    }
    public static ProjectDtoInfo convertProjectsDtoToProjectDtoInfo(ProjectsDto projectsDto) throws DatatypeConfigurationException {

        ProjectDtoInfo projectDtoInfo = new ProjectDtoInfo();

        projectDtoInfo.setProjectId(projectsDto.getProjectId());
        projectDtoInfo.setDescription(projectsDto.getDescription());
        projectDtoInfo.setDateAdded(DatatypeFactory
                .newInstance()
                .newXMLGregorianCalendar(projectsDto.getDateAdded().toString()));
        projectDtoInfo.setCountOfDevelopers(projectsDto.getCountOfDevelopers());

        return projectDtoInfo;
    }

    public static List<ProjectsDto> convertProjectInfosListToProjectDtoList
            (List<ProjectDtoInfo> projectsDtoInfoList) throws DatatypeConfigurationException {

        List<ProjectsDto> projectsDtoList = new ArrayList<>();

        for (int i = 0; i < projectsDtoInfoList.size(); i++) {

            projectsDtoList.add(convertProjectsDtoInfoToProjectDto(projectsDtoInfoList.get(i)));
        }
        return projectsDtoList;
    }
}


