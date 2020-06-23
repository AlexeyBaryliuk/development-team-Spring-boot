package com.epam.brest.courses.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * POJO Projects for model.
 */


@Entity
@Table(name = "projectsDto")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@SuppressFBWarnings(value = { "EI_EXPOSE_REP", "EI_EXPOSE_REP2" }
        , justification = "I prefer to suppress these FindBugs warnings")
public class ProjectsDto {

    /**
     * Project id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public  LocalDate getDateAdded() {
        return dateAdded;
    }


}
