package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects;
import com.epam.brest.courses.model.Projects_Developers;
import com.epam.brest.courses.model.dto.ProjectsDto;
import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.epam.brest.courses.daoJPA")
@ComponentScan({"com.epam.brest.courses.*"})
//@EntityScan("com.epam.brest.courses.model")
//@MapperScan("com.epam.brest.courses.myBatis")
//@MappedTypes({Developers.class
//        , Projects.class
//        , ProjectsDto.class
//        , Projects_Developers.class})
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class);
    }
}