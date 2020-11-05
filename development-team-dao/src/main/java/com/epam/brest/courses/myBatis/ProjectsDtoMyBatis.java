package com.epam.brest.courses.myBatis;

import com.epam.brest.courses.model.dto.ProjectsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ProjectsDtoMyBatis {

    /**
     * Find all projects between two dates.
     *
     * @param dateStart
     * @param dateEnd
     * @return project's list.
     */
    @Select( "SELECT p.projectId AS ProjectId , p.dateAdded AS DateAdded,p.description AS Description, " +
            "COUNT(developerId) AS CountOfDevelopers  FROM projects p LEFT JOIN projects_developers  pd " +
            "ON p.projectId = pd.projectId  WHERE p.dateAdded BETWEEN #{dateStart} AND #{dateEnd} GROUP BY p.projectId")
    List<ProjectsDto> findAllByDateAddedBetween(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);

    /**
     * Count of developers
     *
     * @return project's list.
     */
    @Select("SELECT p.projectId AS ProjectId , p.dateAdded AS DateAdded,p.description AS Description, " +
            "COUNT(developerId) AS CountOfDevelopers FROM projects p LEFT JOIN projects_developers  pd " +
            "ON p.projectId = pd.projectId GROUP BY p.projectId")
    List<ProjectsDto> countOfDevelopers();
}