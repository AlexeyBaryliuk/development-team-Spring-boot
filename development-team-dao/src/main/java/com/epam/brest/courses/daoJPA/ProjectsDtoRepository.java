package com.epam.brest.courses.daoJPA;

import com.epam.brest.courses.dao.ProjectsDaoDto;
import com.epam.brest.courses.model.dto.ProjectsDto;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Profile("jpa")
public interface ProjectsDtoRepository extends JpaRepository<ProjectsDto, Integer>, ProjectsDaoDto {

    /**
     * Find all projects between two dates.
     *
     * @param dateStart
     * @param dateEnd
     * @return project's list.
     */
    @Query(value = "SELECT p.projectId AS ProjectId , p.dateAdded AS DateAdded,p.description AS Description, " +
            "COUNT(developerId) AS CountOfDevelopers  FROM projects p LEFT JOIN projects_developers  pd " +
            "ON p.projectId = pd.projectId  WHERE p.dateAdded BETWEEN :dateStart AND :dateEnd GROUP BY p.projectId"
            , nativeQuery = true)
    List<ProjectsDto> findAllByDateAddedBetween(@Param("dateStart") LocalDate dateStart,@Param("dateEnd") LocalDate dateEnd);

    /**
     * Count of developers
     *
     * @return project's list.
     */
    @Query(value = "SELECT p.projectId AS ProjectId , p.dateAdded AS DateAdded,p.description AS Description, " +
            "COUNT(developerId) AS CountOfDevelopers FROM projects p LEFT JOIN projects_developers  pd " +
            "ON p.projectId = pd.projectId GROUP BY p.projectId", nativeQuery = true)
    List<ProjectsDto> countOfDevelopers();
}
