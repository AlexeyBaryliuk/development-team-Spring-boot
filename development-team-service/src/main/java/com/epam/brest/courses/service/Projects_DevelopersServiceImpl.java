package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.Projects_DevelopersJdbcDao;
import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects_Developers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class Projects_DevelopersServiceImpl implements Projects_DevelopersService {

private static final Logger LOGGER = LoggerFactory.getLogger(Projects_DevelopersServiceImpl.class);


private final Projects_DevelopersJdbcDao projects_developersJdbcDao;

    public Projects_DevelopersServiceImpl(Projects_DevelopersJdbcDao projects_developersJdbcDao) {
        this.projects_developersJdbcDao = projects_developersJdbcDao;
    }


    @Override
    public List<Developers> selectDevelopersFromProjects_Developers(Integer projectId) {

        LOGGER.debug("SERVICE selectDevelopersFromProjects_Developers(). Project id = {}", projectId);

        List<Developers> developersList = projects_developersJdbcDao.selectDevelopersFromProjects_Developers(projectId);
        LOGGER.debug("Developer's list size = {}", developersList.size());
        return developersList;
    }

    @Override
    public Integer addDeveloperToProjects_Developers(Integer projectId, Integer developerId) {

        LOGGER.debug("SERVICE addDeveloperToProjects_Developers(). Project id = {}, Developer id = {}", projectId, developerId);
        return projects_developersJdbcDao.addDeveloperToProjects_Developers(projectId,developerId);
    }

    @Override
    public Integer deleteDeveloperFromProject_Developers(Integer projectId, Integer developerId) {

        LOGGER.debug("SERVICE deleteDeveloperFromProject_Developers(). Developer id = {}", developerId);
        return projects_developersJdbcDao.deleteDeveloperFromProject_Developers(projectId, developerId);
    }

    @Override
    public Optional<Projects_Developers> findByIdFromProjects_Developers(Integer projectId, Integer developerId) {

        LOGGER.debug("SERVICE findByIdFromProjects_Develoers(). Developer id = {}. ProjectId = {}", developerId, projectId);
        Optional<Projects_Developers> projects_developers = projects_developersJdbcDao.findByIdFromProjects_Developers(projectId,developerId);
        return projects_developers;
    }


}
