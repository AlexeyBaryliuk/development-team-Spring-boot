package com.epam.brest.courses.model;

public class Projects_Developers {

    /**
     * Project's id.
     */
    private Integer projectId;

    /**
     * Developer's id.
     */
    private Integer developerId;

    /**
     * Returns <code>Integer</code> representation of this projectId.
     *
     * @return projectId
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * Sets the project's identifier.
     *
     * @param projectId Project's Id.
     */
    public Projects_Developers setProjectId(Integer projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * Returns <code>Integer</code> representation of this developerId.
     *
     * @return developerId
     */
    public Integer getDeveloperId() {
        return developerId;
    }

    /**
     * Sets the developer's identifier.
     *
     * @param developerId Developer's Id.
     */
    public Projects_Developers setDeveloperId(Integer developerId) {
        this.developerId = developerId;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Projects_Developers{" +
                "projectId=" + projectId +
                ", developerId=" + developerId +
                '}';
    }

}
