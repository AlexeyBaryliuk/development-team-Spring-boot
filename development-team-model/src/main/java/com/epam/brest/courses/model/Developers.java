package com.epam.brest.courses.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Developers.
 */
@Entity
@ToString
@Table(name = "developers")
public class Developers {


    public Developers() {
    }

    public Developers(Integer developerId, String firstName, String lastName) {
        this.developerId = developerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

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

    public Integer getDeveloperId() {
        return developerId;
    }

    public Developers setDeveloperId(Integer developerId) {
        this.developerId = developerId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Developers setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Developers setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Set<Projects> getProjects() {
        return projects;
    }

    public Developers setProjects(Set<Projects> projects) {
        this.projects = projects;
        return this;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "developers",fetch= FetchType.LAZY)
    Set<Projects> projects;

}
