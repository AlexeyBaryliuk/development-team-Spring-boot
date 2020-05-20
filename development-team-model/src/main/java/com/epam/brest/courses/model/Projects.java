package com.epam.brest.courses.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Projects.
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
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

    /**
     * Returns <code>Integer</code> representation of this projectId.
     *
     * @return projectId Project Id.
     */
    public Integer getProjectId() {
        return projectId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate getDateAdded() {
        return dateAdded;
    }

@JsonIgnore
@ManyToMany(fetch = FetchType.LAZY)
@JoinTable(name="projects_developers",
        joinColumns=@JoinColumn(name="projectId"),
        inverseJoinColumns = @JoinColumn(name="developerId"))
private Set<Developers> developers;

}
