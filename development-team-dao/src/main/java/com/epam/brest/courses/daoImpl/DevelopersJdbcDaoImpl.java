package com.epam.brest.courses.daoImpl;

import com.epam.brest.courses.dao.DevelopersJdbcDao;
import com.epam.brest.courses.model.Developers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.model.constants.DeveloperConstants.*;

@Component
@PropertySource("classpath:sql-development-team.properties")
public class DevelopersJdbcDaoImpl implements DevelopersJdbcDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevelopersJdbcDaoImpl.class);
    @Autowired
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DevelopersJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private MapSqlParameterSource parameterSource = new MapSqlParameterSource();

    GeneratedKeyHolderFactory keyHolderFactory = new GeneratedKeyHolderFactory();

    @Value("${DEV.sqlGetAllDevelopers}")
    private String sqlGetAllDevelopers;

    @Value("${DEV.sqlGetDeveloperById}")
    private String sqlGetDeveloperById;

    @Value("${DEV.sqlAdd}")
    private String  sqlAdd;

    @Value("${DEV.sqlUpdate}")
    private String sqlUpdate;

    @Value("${DEV.sqlDeleteById}")
    private String sqlDeleteById;

    @Override
    public List<Developers> findAll() {

        LOGGER.debug("FindAll");
        List<Developers> developersList = namedParameterJdbcTemplate
                .query(sqlGetAllDevelopers, new BeanPropertyRowMapper<>(Developers.class));
        return developersList;
    }

    @Override
    public Optional<Developers> findByDeveloperId(Integer developerId) {

        LOGGER.debug("findByDeveloperIddeveloperId = {}", developerId);
        parameterSource.addValue(DEVELOPER_ID, developerId);
        List<Developers> developersList = namedParameterJdbcTemplate
                .query(sqlGetDeveloperById,parameterSource,new BeanPropertyRowMapper<>(Developers.class));
        return Optional.ofNullable(DataAccessUtils.uniqueResult(developersList));
    }

    @Override
    public Integer create(Developers developer) {

        LOGGER.debug("Create developer = {} ", developer);
        parameterSource.addValue(LASTNAME, developer.getLastName());
        parameterSource.addValue(FIRSTNAME, developer.getFirstName());

        KeyHolder keyHolder = keyHolderFactory.newKeyHolder();

        Integer res = namedParameterJdbcTemplate.update(sqlAdd, parameterSource, keyHolder);
        LOGGER.debug("Result = {} ", res);
        return  keyHolder.getKey().intValue();
    }

    @Override
    public Integer update(Developers developer) {

        LOGGER.debug("Update developer = {}", developer);
        parameterSource.addValue(DEVELOPER_ID, developer.getDeveloperId());
        parameterSource.addValue(FIRSTNAME, developer.getFirstName());
        parameterSource.addValue(LASTNAME, developer.getLastName());
        int result = namedParameterJdbcTemplate.update(sqlUpdate, parameterSource);

        LOGGER.debug("Result = {}", result);
        return result;
    }

    @Override
    public Integer delete(Integer developerId) {

        LOGGER.debug("Delete developerId = {}", developerId);
        parameterSource.addValue(DEVELOPER_ID, developerId);
       Integer result =  namedParameterJdbcTemplate.update(sqlDeleteById,parameterSource);

        return result;
    }
}
