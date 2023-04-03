package io.github.mat3e.model;

import java.time.ZonedDateTime;

public class Task {
    private int id;
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private String additionalComment;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    public String getAdditionalComment() {
        return additionalComment;
    }
}
