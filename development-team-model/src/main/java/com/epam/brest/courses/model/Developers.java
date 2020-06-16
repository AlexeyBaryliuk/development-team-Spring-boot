package com.epam.brest.courses.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Objects;
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

    @Transient
    private String fileType;

    @Transient
    private MultipartFile multipartFile;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developers that = (Developers) o;
        return developerId.equals(that.developerId) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(developerId, firstName, lastName);
    }
}
