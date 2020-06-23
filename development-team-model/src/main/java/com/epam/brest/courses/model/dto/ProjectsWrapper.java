package com.epam.brest.courses.model.dto;

import java.util.ArrayList;
import java.util.List;


public class ProjectsWrapper {

    public ProjectsWrapper() {
    }

    public ProjectsWrapper(List<ProjectsDto> projectsDtoList) {
        this.projectsDtoList = projectsDtoList;
    }

    private List<ProjectsDto> projectsDtoList = new ArrayList<>();

    public List<ProjectsDto> getProjectsDtoList() {
        return projectsDtoList;
    }

    public void setProjectsDtoList(List<ProjectsDto> projectsDtoList) {
        this.projectsDtoList = projectsDtoList;
    }
}
