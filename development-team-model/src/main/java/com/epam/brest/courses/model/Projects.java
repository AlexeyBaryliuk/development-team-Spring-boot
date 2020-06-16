package com.epam.brest.courses.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * Projects.
 */
@Entity
@Data
@Table(name = "projects")
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
     * Project id.
     */
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateAdded;

    @Transient
    private String fileType;

    @Transient
    private MultipartFile multipartFile;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate getDateAdded() {
        return dateAdded;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="projects_developers",
            joinColumns=@JoinColumn(name="projectId"),
            inverseJoinColumns = @JoinColumn(name="developerId"))
    Set<Developers> developers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Projects projects = (Projects) o;
        return projectId.equals(projects.projectId) &&
                Objects.equals(description, projects.description) &&
                Objects.equals(dateAdded, projects.dateAdded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, description, dateAdded);
    }
}
