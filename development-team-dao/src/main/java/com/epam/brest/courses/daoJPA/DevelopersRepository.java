package com.epam.brest.courses.daoJPA;

import com.epam.brest.courses.dao.DevelopersDao;
import com.epam.brest.courses.model.Developers;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface DevelopersRepository extends JpaRepository<Developers, Integer>, DevelopersDao {


    /**
     * Persist new developer.
     *
     * @param developer developer.
     * @return persisted developer's id.
     */
    @Modifying
    @Query(value="INSERT INTO developers (developerId, firstName, lastName) VALUES (:#{#dev.developerId}, :#{#dev.firstName}, :#{#dev.lastName})",
            nativeQuery = true)
    Integer create(@Param("dev")Developers developer);
    /**
     * Update developer.
     *
     * @param developer developer.
     * @return number of updated records in the database.
     */
    @Modifying
    @Query(value="UPDATE developers SET firstName=:#{#dev.firstName}, lastName = :#{#dev.lastName} WHERE developerId = :#{#dev.developerId}",
            nativeQuery = true)
    Integer update (@Param("dev")Developers developer);

    /**
     * Get count of developers.
     *
     * @return Count of developers.
     */
    @Query(value="SELECT count(*) AS Count FROM developers",
            nativeQuery = true)
    Integer countOfRow();

    @Modifying
    @Query(value="DELETE FROM developers",
            nativeQuery = true)
    Integer deleteAllDevelopers();
}
