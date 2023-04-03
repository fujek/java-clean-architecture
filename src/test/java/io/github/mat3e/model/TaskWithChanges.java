package io.github.mat3e.model;

import java.time.ZonedDateTime;

public class TaskWithChanges {
    private int id;
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private String additionalComment;
    private int changesCount;

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

    public int getChangesCount() {
        return changesCount;
    }
}
