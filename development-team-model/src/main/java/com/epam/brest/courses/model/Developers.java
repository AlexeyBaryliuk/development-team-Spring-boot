package com.epam.brest.courses.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Developers.
 */

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Developers {

    /**
     * Developer's id.
     */

    private Integer developerId;

    /**
     * Developer's firstName.
     */
    private String firstName;

    /**
     * Developer's lastName.
     */
    private String lastName;



}
