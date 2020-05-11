package com.epam.brest.courses.model;

/**
 * Developers.
 */
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

    /**
     * Constructor without arguments.
     */
    public Developers() {
    }

    /**
     * Returns <code>Integer</code> representation of this developer's id.
     *
     * @return developerId Developer's id.
     */
    public Integer getDeveloperId() {
        return developerId;
    }

    /**
     * Sets the developer's identifier.
     *
     * @param developerId Developer's id.
     */
    public Developers setDeveloperId(Integer developerId)
    {
        this.developerId = developerId;
        return this;
    }


    /**
     * Returns <code>String</code> representation of this developer's firstName.
     *
     * @return developerId Developer's firstName.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the developer's firstName.
     *
     * @param firstName Developer's firstName.
     */
    public Developers setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }


    /**
     * Returns <code>String</code> representation of this developer's lastName.
     *
     * @return lastName Developer's lastName.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the developer's lastName.
     *
     * @param lastName Developer's lastName.
     */
    public Developers setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Returns <code>String</code> representation of this description.
     *
     * @return description Developer's description.
     */
    @Override
    public String toString() {
        return "Developers{"
                + "developerId=" + developerId
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + '}';
    }
}
