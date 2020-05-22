package com.epam.brest.courses.daoImpl;


import com.epam.brest.courses.model.Developers;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class DevelopersMockTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevelopersMockTest.class);

    @InjectMocks
    private DevelopersJdbcDaoImpl developersJdbcDao;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void after(){
        verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }


    @Test
    public void shouldGetAll(){

        Developers developer = newDeveloper();
        String sql = "something";
        ReflectionTestUtils.setField(developersJdbcDao,"sqlGetAllDevelopers", sql);

        when(namedParameterJdbcTemplate
                .query(anyString(), any(BeanPropertyRowMapper.class))).thenReturn(Collections.singletonList(developer));


        List<Developers> testList = developersJdbcDao.findAll();
        assertNotNull(testList);
        assertEquals(1,testList.size());

        Developers developerTest = testList.get(0);
        assertNotNull(developerTest);
        assertSame(developerTest, developer);
    }

    @Test
    public void shouldFindDeveloperById(){

        Developers developer = newDeveloper();
        String sql = "something";
        List<Developers> developersList = new LinkedList<>();
        developersList.add(developer);
        ReflectionTestUtils.setField(developersJdbcDao,"sqlGetDeveloperById", sql);

        when(namedParameterJdbcTemplate.query(anyString(), any(MapSqlParameterSource.class)
                ,any(BeanPropertyRowMapper.class))).thenReturn(developersList);

        Optional<Developers> optionalDeveloper = developersJdbcDao.findByDeveloperId(1);

        assertEquals("Jon", optionalDeveloper.get().getFirstName());
        assertEquals("Connor", optionalDeveloper.get().getLastName());
    }


//    @Test
//    public void shouldCreateNewDeveloper(){
//
//        Developers developer = newDeveloper();
//        String sql = "something";
//        ReflectionTestUtils.setField(developersJdbcDao,"sqlAdd", sql);
//
//        KeyHolder keyHolder = mock(GeneratedKeyHolder.class);
//
//        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class)
//                ,any(KeyHolder.class))).thenReturn(0);
//
//        when((keyHolder.getKey())).thenReturn(1 );
//        LOGGER.debug("Id------ = {} ", keyHolder.getKey());
//
//        developersJdbcDao.create(developer);
//
//        assertEquals(1, keyHolder.getKey());
//
//        }

    @Test
    public void shouldDeleteDeveloper(){

        String sql = "something";

        ReflectionTestUtils.setField(developersJdbcDao,"sqlDeleteById", sql);
        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class))).thenReturn(1);

        Integer result = developersJdbcDao.delete(1);
        assertTrue(result > 0);


    }

    private static Developers newDeveloper(){

        Developers developer = new Developers();
        developer.setFirstName("Jon");
        developer.setLastName("Connor");
        return developer;
    }
}
