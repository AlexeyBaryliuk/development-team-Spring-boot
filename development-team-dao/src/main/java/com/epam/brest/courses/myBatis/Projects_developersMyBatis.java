package com.epam.brest.courses.myBatis;

import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects_Developers;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;


@Mapper
public interface Projects_developersMyBatis {
    /**
     * Select all developers from project by projectId.
     *
     * @return developer's list.
     */
    @Select("SELECT  d.firstName AS FirstName ,d.lastName AS LastName, d.developerId AS DeveloperId \" +\n" +
            "            \"FROM projects_developers  pd JOIN developers d ON d.developerId=pd.developerId \" +\n" +
            "            \"WHERE pd.projectId=#{projectId} ORDER BY pd.projectId ASC")
    List<Developers> selectDevelopersFromProjects_Developers(@Param("projectId")Integer projectId);

    /**
     * Persist developer to project.
     *
     * @param projectId, developerId
     * @return number of updated records in the database.
     */
    @Insert("INSERT INTO projects_developers ( projectId, developerId) VALUES (#{projectId}, #{developerId})")
    Integer addDeveloperToProjects_Developers(@Param("projectId")Integer projectId, @Param("developerId")Integer developerId);

    /**
     * Delete developer from Projects_Developers.
     *
     * @param projectId,developerId
     * @return number of updated records in the database.
     */
    @Delete("DELETE FROM projects_developers WHERE projectId=#{projectId} AND developerId = #{developerId}")
    Integer deleteDeveloperFromProject_Developers(@Param("projectId")Integer projectId, @Param("developerId") Integer developerId);

    /**
     *Find developer By Id from projects_developers.
     *
     * @param projectId
     * @param developerId
     * @return developer
     */

    @Select("SELECT  projectId, developerId FROM projects_developers WHERE projectId = #{projectId} AND developerId = #{developerId}")
    Optional<Projects_Developers> findByIdFromProjects_Developers(@Param("projectId")Integer projectId, @Param("developerId") Integer developerId);
}

