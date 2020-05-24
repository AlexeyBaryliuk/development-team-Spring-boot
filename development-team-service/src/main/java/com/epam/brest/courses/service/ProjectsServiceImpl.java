package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.ProjectsDao;
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

    private final ProjectsDao ProjectsDao;

    public ProjectsServiceImpl(ProjectsDao ProjectsDao) {
        this.ProjectsDao = ProjectsDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Projects> findAll() {

        LOGGER.debug("Find all projects - findAll()");
        List<Projects> projectsList = ProjectsDao.findAll();
        return projectsList;
    }

    @Override
    public Optional<Projects> findByDeveloperId(Integer projectId) {

        LOGGER.debug("Find by id - findByDeveloperId() projectId = {}", projectId);
        return ProjectsDao.findByProjectId(projectId);
    }

    @Override
    public Integer update(Projects project) {

        LOGGER.debug("Update project - update(): project = {}",project);
        return ProjectsDao.update(project);
    }

    @Override
    public Integer create(Projects project) {

        LOGGER.debug("Create project - create(): project = {}",project);
        return ProjectsDao.create(project);
    }

    @Override
    public Integer delete(Integer projectId) {

        LOGGER.debug("Delete project - delete() projectId = {}",projectId);
        return ProjectsDao.deleteByProjectId(projectId);
    }
}
