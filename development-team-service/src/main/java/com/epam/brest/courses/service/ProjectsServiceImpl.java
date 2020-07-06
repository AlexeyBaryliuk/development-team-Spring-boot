package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.ProjectsDao;
import com.epam.brest.courses.model.Projects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.model.constants.ProjectConstants.PROJECT_ID;

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

    @SuppressWarnings("ConstantConditions")
    private boolean isIdUnique(Projects project) {

        if((projectsDao.findByProjectId(project.getProjectId()).isPresent())){
    return false;
        }

        return true;
    }

    @Override
    public Integer create(Projects project) {
        String projectId ="";
        if (project.getProjectId()!= null) {
            projectId = project.getProjectId().toString();
        }
        if (!isIdUnique(project)){

            project.setProjectId(null);
            project.setDescription(project.getDescription()
                    + ". Project with projectId = " + projectId + " already exist.  ");
            System.out.println("----------------------------------------isUniqId____________________");
        }
        LOGGER.debug("Create project - create(): project = {}",project);
        return projectsDao.create(project);
    }

    @Override
    public Integer delete(Integer projectId) {

        LOGGER.debug("Delete project - delete() projectId = {}",projectId);
        return projectsDao.deleteByProjectId(projectId);
    }

    @Override
    public Integer deleteAllProjects() {

        LOGGER.debug("deleteAll()");
        return projectsDao.deleteAllProjects();
    }
}
