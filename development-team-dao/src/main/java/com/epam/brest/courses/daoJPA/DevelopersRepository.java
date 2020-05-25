package com.epam.brest.courses.daoJPA;

import com.epam.brest.courses.dao.DevelopersDao;
import com.epam.brest.courses.model.Developers;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("jpa")
public interface DevelopersRepository extends JpaRepository<Developers, Integer>, DevelopersDao {

    @Query(value="SELECT developerId, firstName, lastName FROM developers d ORDER BY d.developerId",
            nativeQuery = true)
    List<Developers> findAll();
    /**
     * Persist new developer.
     *
     * @param developer developer.
     * @return persisted developer's id.
     */
    @Modifying
    @Query(value="INSERT INTO developers (firstName, lastName) VALUES (:#{#dev.firstName}, :#{#dev.lastName})",
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

}
