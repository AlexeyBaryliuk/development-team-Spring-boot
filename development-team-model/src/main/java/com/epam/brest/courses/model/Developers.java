package com.epam.brest.courses.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Developers.
 */
@Entity
@Data
@Table(name = "developers")
public class Developers {


    /**
     * Developer's id.
     */
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Integer developerId;

    /**
     * Developer's firstName.
     */
    private String firstName;

    /**
     * Developer's lastName.
     */
    private String lastName;

    @JsonIgnore
    @ManyToMany(mappedBy = "developers",fetch= FetchType.LAZY)
    Set<Projects> projects;

}
