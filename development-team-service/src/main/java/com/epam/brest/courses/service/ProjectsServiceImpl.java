package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.ProjectsJdbcDao;
import com.epam.brest.courses.model.Projects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectsServiceImpl implements ProjectsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsServiceImpl.class);

    private final ProjectsJdbcDao projectsJdbcDao;

    public ProjectsServiceImpl(ProjectsJdbcDao projectsJdbcDao) {
        this.projectsJdbcDao = projectsJdbcDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Projects> findAll() {

        LOGGER.debug("Find all projects - findAll()");
        List<Projects> projectsList = projectsJdbcDao.findAll();
        return projectsList;
    }

    @Override
    public Optional<Projects> findByDeveloperId(Integer projectId) {

        LOGGER.debug("Find by id - findByDeveloperId() projectId = {}", projectId);
        return projectsJdbcDao.findByProjectId(projectId);
    }

    @Override
    public Integer update(Projects project) {

        LOGGER.debug("Update project - update(): project = {}",project);
        return projectsJdbcDao.update(project);
    }

    @Override
    public Integer save(Projects project) {

        LOGGER.debug("Create project - save(): project = {}",project);
        return projectsJdbcDao.save(project);
    }

    @Override
    public Integer delete(Integer projectId) {

        LOGGER.debug("Delete project - delete() projectId = {}",projectId);
        return projectsJdbcDao.deleteByProjectId(projectId);
    }
}
