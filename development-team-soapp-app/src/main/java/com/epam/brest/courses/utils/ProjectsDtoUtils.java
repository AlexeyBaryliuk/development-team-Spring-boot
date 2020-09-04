package com.epam.brest.courses.utils;

import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.soap_api.ProjectDtoInfo;

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

    public static XMLGregorianCalendar convertLocalDateToXMLGregorianCalendar
            (LocalDate in) throws DatatypeConfigurationException {

        return DatatypeFactory.newInstance().newXMLGregorianCalendar(in.toString());
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

    public static List<ProjectDtoInfo> convertProjectsListToProjectDtoInfoList
            (List<ProjectsDto> projectsDtoList) throws DatatypeConfigurationException {

        List<ProjectDtoInfo> projectDtoInfos = new ArrayList<>();

        for (int i = 0; i < projectsDtoList.size(); i++) {

            projectDtoInfos.add(convertProjectsDtoToProjectDtoInfo(projectsDtoList.get(i)));
        }
        return projectDtoInfos;
    }
}

