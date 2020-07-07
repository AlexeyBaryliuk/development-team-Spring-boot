package com.epam.brest.courses.daoImpl;

import com.epam.brest.courses.dao.DevelopersDao;
import com.epam.brest.courses.model.Developers;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.model.constants.DeveloperConstants.*;

@Component
@PropertySource("classpath:sql-development-team.properties")
@Profile("jdbc")
@SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
public class DevelopersJdbcDaoImpl implements DevelopersDao {

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

    @Value("${DEV.sqlCountOfRow}")
    private String sqlCountOfRow;

    @Value("${DEV.sqlDeleteAll}")
    private String sqlDeleteAll;

    @Override
    public List<Developers> findAll() {

        LOGGER.debug("FindAll");
        List<Developers> developersList = namedParameterJdbcTemplate
                .query(sqlGetAllDevelopers, new BeanPropertyRowMapper<>(Developers.class));
        return developersList;
    }

    @Override
    public Optional<Developers> findByDeveloperId(Integer developerId) {

        LOGGER.debug("findByDeveloperId = {}", developerId);

        parameterSource.addValue(DEVELOPER_ID, developerId);
        List<Developers> developersList = namedParameterJdbcTemplate
                .query(sqlGetDeveloperById,parameterSource,new BeanPropertyRowMapper<>(Developers.class));
        return Optional.ofNullable(DataAccessUtils.uniqueResult(developersList));
    }

    @Override
    public Integer create(Developers developer) {

        LOGGER.debug("Create developer = {} ", developer);
        parameterSource.addValue(DEVELOPER_ID, developer.getDeveloperId());
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
    public Integer deleteByDeveloperId(Integer developerId) {

        LOGGER.debug("Delete developerId = {}", developerId);
        parameterSource.addValue(DEVELOPER_ID, developerId);
       Integer result =  namedParameterJdbcTemplate.update(sqlDeleteById,parameterSource);

        return result;
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
    public Integer deleteAllDevelopers() {

        LOGGER.debug("deleteAllDevelopers()");
        return namedParameterJdbcTemplate.update(sqlDeleteAll, new MapSqlParameterSource());
    }
}
