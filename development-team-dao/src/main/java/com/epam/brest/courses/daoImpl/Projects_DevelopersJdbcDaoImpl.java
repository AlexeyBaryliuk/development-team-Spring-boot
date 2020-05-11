package com.epam.brest.courses.daoImpl;

import com.epam.brest.courses.dao.Projects_DevelopersJdbcDao;
import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects_Developers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Optional;

public class Projects_DevelopersJdbcDaoImpl implements Projects_DevelopersJdbcDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(Projects_DevelopersJdbcDaoImpl.class);

    MapSqlParameterSource parameterSource = new MapSqlParameterSource();

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Projects_DevelopersJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Value("${DEV.sqlSelectDeveloperFromProjectsDevelopers}")
    private String sqlSelectDeveloperFromProjectsDevelopers;

    @Value("${DEV.sqlAddDeveloperToProjectsDevelopers}")
    private String sqlAddDeveloperToProjectsDevelopers;

    @Value("${DEV.sqlDeleteFromProjectDevelopers}")
    private String sqlDeleteFromProjectDevelopers;

    @Value("${DEV.sqlFindByIdFromProject_Developers}")
    private String sqlFindByIdFromProject_Developers;

    @Override
    public List<Developers> selectDevelopersFromProjects_Developers(Integer projectId) {

        LOGGER.debug("DAO SelectDeveloperFromProjects_Developers(). Project id = {}", projectId);
        parameterSource.addValue("projectId", projectId);
        List<Developers> developersList = namedParameterJdbcTemplate.query(sqlSelectDeveloperFromProjectsDevelopers
                ,parameterSource, new BeanPropertyRowMapper<>(Developers.class));
        return developersList;
    }

    @Override
    public Integer addDeveloperToProjects_Developers(Integer projectId, Integer developerId) {

        LOGGER.debug("DAO addDeveloperToProjects_Developers() Project id = {}. Developer id = {}",projectId,  developerId);
        parameterSource.addValue("projectId", projectId);
        parameterSource.addValue("developerId", developerId);
        Integer result = namedParameterJdbcTemplate.update(sqlAddDeveloperToProjectsDevelopers,parameterSource);
        return result;
    }

    @Override
    public Integer deleteDeveloperFromProject_Developers(Integer progectId, Integer developerId) {

        LOGGER.debug("DAO deleteDeveloperFromProject_Developers(). Developer id = {}, Project id = {}", developerId, progectId);
        parameterSource.addValue("developerId", developerId);
        parameterSource.addValue("projectId", progectId);
        Integer result = namedParameterJdbcTemplate.update(sqlDeleteFromProjectDevelopers,parameterSource);
        return result;
    }

    @Override
    public Optional<Projects_Developers> findByIdFromProjects_Developers(Integer projectId, Integer developerId) {

        LOGGER.debug("DAO. findByIdFromProjects_Develoers();");
        parameterSource.addValue("projectId", projectId);
        parameterSource.addValue("developerId", developerId);

        List<Projects_Developers> developersList = namedParameterJdbcTemplate
                .query(sqlFindByIdFromProject_Developers,parameterSource,new BeanPropertyRowMapper<>(Projects_Developers.class));
        return Optional.ofNullable(DataAccessUtils.uniqueResult(developersList));
    }

}
