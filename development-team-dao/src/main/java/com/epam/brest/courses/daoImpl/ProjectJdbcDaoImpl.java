package com.epam.brest.courses.daoImpl;

import com.epam.brest.courses.dao.ProjectsDao;
import com.epam.brest.courses.model.Projects;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.model.constants.ProjectConstants.*;

@Component
@PropertySource("classpath:sql-development-team.properties")
@Profile("jdbc")
@SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
public class ProjectJdbcDaoImpl implements ProjectsDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectJdbcDaoImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProjectJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Value("${PRO.sqlGetAllProjects}")
    private String sqlGetAllProjects;

    @Value("${PRO.sqlAdd}")
    private String sqlAdd;

    @Value("${PRO.sqlUpdate}")
    private String sqlUpdate;

    @Value("${PRO.sqlGetProjectById}")
    private String sqlGetProjectById;

    @Value("${PRO.sqlDeleteById}")
    private String sqlDeleteById;

    @Value("${PRO.sqlCountOfDescription}")
    private String sqlCountOfDescription;


    @Value("${PRO.sqlCountOfRow}")
    private String sqlCountOfRow;

    @Value("${PRO.sqlDeleteAll}")
    private String sqlDeleteAll;

    private MapSqlParameterSource parameterSource = new MapSqlParameterSource();

    @Override
    public List<Projects> findAll() {

        LOGGER.debug("fidAll()");
        List<Projects> projectsList = namedParameterJdbcTemplate.
                query(sqlGetAllProjects, new BeanPropertyRowMapper<>(Projects.class));

        return projectsList;
    }


    @Override
    public Optional<Projects> findByProjectId(Integer projectId) {

        parameterSource.addValue(PROJECT_ID, projectId);

        List<Projects> projects =  namedParameterJdbcTemplate
                            .query(sqlGetProjectById, parameterSource,
                            new BeanPropertyRowMapper<>(Projects.class));
        LOGGER.debug("List of projects - size=:  {} ", projects.size()) ;

        return Optional.ofNullable(DataAccessUtils.uniqueResult(projects));
    }


    @Override
    public Integer update(Projects project) {

        LOGGER.debug("Date = :      " + project.getDateAdded());
        parameterSource.addValue(PROJECT_ID, project.getProjectId());
        parameterSource.addValue(DESCRIPTION, project.getDescription());
        parameterSource.addValue(DATEADDED, project.getDateAdded());
        return namedParameterJdbcTemplate.update(sqlUpdate, parameterSource);
    }

    @SuppressWarnings("ConstantConditions")
    private boolean isNameUnique(Projects project) {

        return namedParameterJdbcTemplate.queryForObject(sqlCountOfDescription,
                new MapSqlParameterSource(DESCRIPTION, project.getDescription()),
                Integer.class) == 0;
    }

    @Override
    public Integer create(Projects project) {

        if (!isNameUnique(project)) {
            throw new IllegalArgumentException("Project with same description already exist.");
        }

        parameterSource.addValue(PROJECT_ID, project.getProjectId() );
        parameterSource.addValue(DESCRIPTION, project.getDescription());
        parameterSource.addValue(DATEADDED, project.getDateAdded());
        LOGGER.debug("Create new project {}", project);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sqlAdd,parameterSource,keyHolder);
        LOGGER.debug("Date {}", project.getDateAdded());
    return keyHolder.getKey().intValue();
    }

    @Override
    public Integer deleteByProjectId(Integer projectId) {

        LOGGER.debug("Delete project by id {}", projectId);
        parameterSource.addValue(PROJECT_ID, projectId);

    return namedParameterJdbcTemplate.update(sqlDeleteById,parameterSource);
    }

    @Override
    public Integer countOfRow() {

        LOGGER.debug("Count of row ");
        Integer result = namedParameterJdbcTemplate.query(sqlCountOfRow, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                rs.next();
                Integer count = rs.getInt("Count");
                return count;
            }
        });
        return result;
    }

    @Override
    public Integer deleteAllProjects() {

        LOGGER.debug("deleteAll()");

        return namedParameterJdbcTemplate.update(sqlDeleteAll, new MapSqlParameterSource());
    }

}
