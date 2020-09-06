package com.epam.brest.courses.soapService;

import com.epam.brest.courses.model.dto.ProjectsDto;
import com.epam.brest.courses.service.ProjectsDtoService;
import com.epam.brest.courses.wsdl.CountOfDevelopersRequest;
import com.epam.brest.courses.wsdl.CountOfDevelopersResponse;
import com.epam.brest.courses.wsdl.FindAllByDateAddedBetweenRequest;
import com.epam.brest.courses.wsdl.FindAllByDateAddedBetweenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.epam.brest.courses.utils.ProjectsDtoUtils.convertProjectInfosListToProjectDtoList;
import static com.epam.brest.courses.utils.ProjectsDtoUtils.convertProjectsDtoInfoToProjectDto;

public class ProjectsDtoClient extends WebServiceGatewaySupport
        implements ProjectsDtoService
{

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsDtoClient.class);

    @Override
    public List<ProjectsDto> findAllByDateAddedBetween(LocalDate dateStart, LocalDate dateEnd) throws DatatypeConfigurationException {

        LOGGER.debug("SoapService - findAllByDateAddedBetweenRequest");
        FindAllByDateAddedBetweenRequest request = new FindAllByDateAddedBetweenRequest();
        request.setDateStart(DatatypeFactory
                .newInstance()
                .newXMLGregorianCalendar(dateStart.toString()));
        request.setDateEnd(DatatypeFactory
                .newInstance()
                .newXMLGregorianCalendar(dateEnd.toString()));

        FindAllByDateAddedBetweenResponse response = (FindAllByDateAddedBetweenResponse)getWebServiceTemplate()
                .marshalSendAndReceive(request);
        List<ProjectsDto> projectsDtoList = new ArrayList<>();
        for (int i = 0; i < response.getProjectDtoInfo().size(); i++) {
            projectsDtoList.add(convertProjectsDtoInfoToProjectDto(response.getProjectDtoInfo().get(i)));
        }
        return projectsDtoList;
    }

    @Override
    public List<ProjectsDto> countOfDevelopers() throws DatatypeConfigurationException {

        LOGGER.debug("SoapService - countOfDevelopersRequest");
        CountOfDevelopersRequest request = new CountOfDevelopersRequest();

        CountOfDevelopersResponse response = (CountOfDevelopersResponse)getWebServiceTemplate()
                .marshalSendAndReceive(request);

        return convertProjectInfosListToProjectDtoList(response.getProjectDtoInfo());
    }
}

