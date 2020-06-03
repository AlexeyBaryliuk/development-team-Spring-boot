package com.epam.brest.courses.daoJPA;

import com.epam.brest.courses.dao.ProjectsDao;
import com.epam.brest.courses.model.Projects;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface ProjectsRepository extends JpaRepository<Projects, Integer>, ProjectsDao {

    /**
     * Persist new project.
     *
     * @param project project.
     * @return persisted project's id.
     */
    @Modifying
    @Query(value="INSERT INTO projects (description, dateAdded) VALUES (:#{#pro.description}, :#{#pro.dateAdded})",
            nativeQuery = true)
    Integer create(@Param("pro")Projects project);

    /**
     * Update project.
     *
     * @param project project.
     * @return number of updated records in the database.
     */
    @Modifying
    @Query(value="UPDATE projects SET description=:#{#pro.description}, dateAdded = :#{#pro.dateAdded} WHERE projectId = :#{#pro.projectId}",
            nativeQuery = true)
    Integer update (@Param("pro") Projects project);

    /**
     * Get count of projects.
     *
     * @return Count of projects.
     */
    @Query(value="SELECT count(*) AS Count FROM projects",
            nativeQuery = true)
    Integer countOfRow();

}
