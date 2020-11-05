package com.epam.brest.courses.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Projects_Developers{

    /**
     * Project's id.
     */
    private Integer projectId;

    /**
     * Developer's id.
     */
    private Integer developerId;

}
