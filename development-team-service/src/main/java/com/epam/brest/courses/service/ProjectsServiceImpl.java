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

    private final ProjectsDao projectsDao;

    public ProjectsServiceImpl(ProjectsDao ProjectsDao) {
        this.projectsDao = ProjectsDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Projects> findAll() {

        LOGGER.debug("Find all projects - findAll()");
        List<Projects> projectsList = projectsDao.findAll();
        return projectsList;
    }

    @Override
    public Optional<Projects> findByProjectId(Integer projectId) {

        LOGGER.debug("Find by id - findByDeveloperId() projectId = {}", projectId);
        return projectsDao.findByProjectId(projectId);
    }

    @Override
    public Integer update(Projects project) {

        LOGGER.debug("Update project - update(): project = {}",project);
        return projectsDao.update(project);
    }

    @Override
    public Integer create(Projects project) {

        LOGGER.debug("Create project - create(): project = {}",project);
        return projectsDao.saveAndFlush(project).getProjectId();
    }

    @Override
    public Integer delete(Integer projectId) {

        LOGGER.debug("Delete project - delete() projectId = {}",projectId);
        return projectsDao.deleteByProjectId(projectId);
    }
}
