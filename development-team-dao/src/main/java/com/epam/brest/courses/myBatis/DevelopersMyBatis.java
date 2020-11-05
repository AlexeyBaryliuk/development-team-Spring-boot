package com.epam.brest.courses.myBatis;

import com.epam.brest.courses.model.Developers;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DevelopersMyBatis {

    /**
     * Find all developers.
     *
     * @return developers list.
     */
    @Select("SELECT developerId, firstName, lastName FROM developers d ORDER BY d.developerId")
    List<Developers> findAll();

    /**
     * Find developer by Id.
     *
     * @param developerId developer's id.
     * @return developer.
     */
    @Select("SELECT developerId, firstName, lastName FROM developers WHERE developerId = #{developerId}")
    Optional<Developers> findByDeveloperId(@Param("developerId")Integer developerId);

    /**
     * Persist new developer.
     *
     * @param developer developer.
     * @return persisted developer's id.
     */
    @Insert(value="INSERT INTO developers (firstName, lastName) VALUES (#{dev.firstName}, #{dev.lastName})")
    Integer create(@Param("dev")Developers developer);

    /**
     * Update developer.
     *
     * @param developer developer.
     * @return number of updated records in the database.
     */
    @Update("UPDATE developers SET firstName=#{dev.firstName}, lastName = #{dev.lastName} WHERE developerId = #{dev.developerId}")
    Integer update (@Param("dev")Developers developer);

    /**
     * Delete developer.
     *
     * @param developerId developer's id.
     * @return number of updated records in the database.
     */
    @Delete("DELETE FROM developers WHERE developerId = #{developerId}")
    Integer deleteByDeveloperId(@Param("developerId")Integer developerId);

    @Delete("DELETE FROM developers")
    Integer deleteAllDevelopers();
}
