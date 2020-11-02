package com.epam.brest.courses.myBatis;

import com.epam.brest.courses.dao.ProjectsDao;
import com.epam.brest.courses.model.Projects;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


@Profile("myBatis")
public interface ProjectsMyBatis extends ProjectsDao {

    /**
     * Find all projects.
     *
     * @return projects list.
     */
    @Select("SELECT projectId, description, dateAdded FROM projects p ORDER BY p.projectId")
    List<Projects> findAll();

    /**
     * Find project by Id.
     *
     * @param projectId project's id.
     * @return project.
     */
    @Select("SELECT projectId, description, dateAdded FROM projects WHERE projectId = #{projectId}")
    Optional<Projects> findByProjectId(@Param("projectId") Integer projectId);

    /**
     * Update project.
     *
     * @param project project.
     * @return number of updated records in the database.
     */
    @Update("UPDATE projects SET description= #{pro.description}, dateAdded = :#{#pro.dateAdded} WHERE projectId =  #{pro.projectId}")
    Integer update (@Param("pro") Projects project);

    /**
     * Persist new project.
     *
     * @param project project.
     * @return persisted project's id.
     */
    @Insert("INSERT INTO projects (description, dateAdded) VALUES (:#{pro.description}, :#{pro.dateAdded})")
    Integer create(@Param("pro")Projects project);

    /**
     * Delete project.
     *
     * @param projectId project's id.
     * @return number of updated records in the database.
     */
    @Delete("DELETE FROM projects WHERE projectId = #{projectId}")
    Integer deleteByProjectId(@Param("projectId") Integer projectId);
}
