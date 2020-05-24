package com.epam.brest.courses.daoJPA;

import com.epam.brest.courses.dao.Projects_DevelopersDao;
import com.epam.brest.courses.model.Developers;
import com.epam.brest.courses.model.Projects_Developers;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public interface Projects_developersRepository extends JpaRepository<Developers, Integer>
        , Projects_DevelopersDao {

    /**
     * Select all developers from project by projectId.
     *
     * @return developer's list.
     */
    @Modifying
    @Query(value="SELECT  d.firstName AS FirstName ,d.lastName AS LastName, d.developerId AS DeveloperId " +
            "FROM projects_developers  pd JOIN developers d ON d.developerId=pd.developerId " +
            "WHERE pd.projectId=:projectId ORDER BY pd.projectId ASC",
            nativeQuery = true)
    List<Developers> selectDevelopersFromProjects_Developers(@Param("projectId") Integer projectId);

    /**
     * Persist developer to project.
     *
     * @param projectId, developerId
     * @return number of updated records in the database.
     */
    @Modifying
    @Query(value="INSERT INTO projects_developers ( projectId, developerId) VALUES (:projectId, :developerId)",
            nativeQuery = true)
    Integer addDeveloperToProjects_Developers(@Param("projectId")Integer projectId, @Param("developerId")Integer developerId);

    /**
     * Delete developer from Projects_Developers.
     *
     * @param projectId,developerId
     * @return number of updated records in the database.
     */
    @Modifying
    @Query(value="DELETE FROM projects_developers WHERE projectId=:projectId AND developerId = :developerId",
            nativeQuery = true)
    Integer deleteDeveloperFromProject_Developers(@Param("projectId")Integer projectId, @Param("developerId") Integer developerId);

    /**
     *Find developer By Id from projects_developers.
     *
     * @param projectId
     * @param developerId
     * @return developer
     */
    @Query(value="SELECT  projectId, developerId FROM projects_developers WHERE projectId = :projectId AND developerId = :developerId",
            nativeQuery = true)
    Optional<Projects_Developers> findByIdFromProjects_Developers(@Param("projectId")Integer projectId, @Param("developerId") Integer developerId);
}

