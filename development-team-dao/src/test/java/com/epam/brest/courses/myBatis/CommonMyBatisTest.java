package com.epam.brest.courses.myBatis;

import com.epam.brest.courses.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Sql({"classpath:schema.sql"})
@ContextConfiguration(classes = TestConfig.class)
public class CommonMyBatisTest {

    @Test
    public void contextLoads() {
    }
}
