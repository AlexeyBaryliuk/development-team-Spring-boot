package com.epam.brest.courses.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * POJO Projects for model.
 */
@SuppressFBWarnings(value = { "EI_EXPOSE_REP", "EI_EXPOSE_REP2" }
        , justification = "I prefer to suppress these FindBugs warnings")
public class ProjectsDto {


    public ProjectsDto() {
    }

    /**
     * Project id.
     */
    private Integer projectId;

    /**
     * Project description.
     */
    private String description;

    /**
     * Date adding of project.
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateAdded;

    /**
     * Count of developers.
     */
    private Integer countOfDevelopers;

     /**
     * Returns <code>Integer</code> representation of this projectId.
     *      *
     *      * @return projectId Project Id.
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * Sets the department's identifier.
     *
     * @param projectId Project Id.
     */
    public  ProjectsDto setProjectId( Integer projectId) {
        this.projectId = projectId;
        return this;
    }

    public ProjectsDto setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    /**
     * Returns <code>Date</code> representation of this add date.
     *
     * @return dateAdded Project add date.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public  LocalDate getDateAdded() {
        return dateAdded;
    }
    /**
     * Returns <code>String</code> representation of this description.
     *
     * @return description Project description.
     */
    public  String getDescription() {
        return description;
    }


    /**
     * Sets the description.
     *
     * @param description Project description.
     */
    public  ProjectsDto setDescription(String description) {
        this.description = description;
        return this;
    }
    /**
     * Returns <code>Integer</code> representation project's count of developers.
     *
     * @return countOfDevelopers Project's count of developers.
     */
    public  Integer getCountOfDevelopers() {
        return countOfDevelopers;
    }

    /**
     * Sets the project's count of developers.
     *
     * @param countOfDevelopers Count of developers.
     */
    public  ProjectsDto setCountOfDevelopers( Integer countOfDevelopers) {
        this.countOfDevelopers = countOfDevelopers;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public  String toString() {
        return "ProjectsDto{"
                + "projectId=" + projectId
                + ", description='" + description + '\''
                + ", dateAdded=" + dateAdded
                + ", countOfDevelopers=" + countOfDevelopers
                + '}';
    }
}
