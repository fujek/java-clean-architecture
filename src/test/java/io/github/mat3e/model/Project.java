package io.github.mat3e.model;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private int id;
    private String name;
    private List<ProjectStep> steps = new ArrayList<>();

    public int getId() {
        return id;
    }
    

    public String getName() {
        return name;
    }


    public List<ProjectStep> getSteps() {
        return steps;
    }

}
