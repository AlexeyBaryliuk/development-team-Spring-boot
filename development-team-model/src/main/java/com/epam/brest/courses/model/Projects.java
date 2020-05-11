package com.epam.brest.courses.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Projects.
 */
@SuppressFBWarnings(value = { "EI_EXPOSE_REP", "EI_EXPOSE_REP2" }
                    , justification = "I prefer to suppress these FindBugs warnings")
public class Projects {

    /**
     * Constructor without arguments.
     */

    public Projects() {
        this.dateAdded=LocalDate.now();
    }

    /**
     * Constructor with arguments
     * @param projectId
     * @param description
     * @param dateAdded
     */
    public Projects(Integer projectId, String description, LocalDate dateAdded) {
        this.projectId = projectId;
        this.description = description;
        this.dateAdded = dateAdded;
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
     * Returns <code>Integer</code> representation of this projectId.
     *
     * @return projectId Project Id.
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * Sets the project's identifier.
     *
     * @param projectId Project's Id.
     */
    public Projects setProjectId(Integer projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * Returns <code>String</code> representation of this description.
     *
     * @return description Project description.
     */
    public String getDescription() {
        return description;
    }


    /**
     * Sets the description.
     *
     * @param description Project description.
     */
    public Projects setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Returns <code>Date</code> representation of this add date.
     *
     * @return dateAdded Project add date.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate getDateAdded() {
        return dateAdded;
    }

    /**
     * Sets the dateAdded.
     *
     * @param dateAdded eAdded Project dateAdded.
     */
    public Projects setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Project{"
                + "projectId=" + projectId
                + ", description='" + description + '\''
                + ", dateAdded=" + dateAdded
                + '}';
    }

}
