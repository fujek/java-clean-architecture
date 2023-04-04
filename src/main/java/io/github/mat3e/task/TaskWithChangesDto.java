package io.github.mat3e.task;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

class TaskWithChangesDto {
    private int id;
    @NotNull
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private int changesCount;

    TaskWithChangesDto(Task source) {
        id = source.getId();
        description = source.getDescription();
        done = source.isDone();
        deadline = source.getDeadline();
        changesCount = source.getChangesCount();
    }

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


    public int getChangesCount() {
        return changesCount;
    }

}
