package io.github.mat3e.dto;

import io.github.mat3e.entity.Task;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

public class TaskDto {
    private int id;
    @NotNull
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private String additionalComment;

    public TaskDto() {
    }

    public TaskDto(Task source) {
        id = source.getId();
        description = source.getDescription();
        done = source.isDone();
        deadline = source.getDeadline();
        additionalComment = source.getAdditionalComment();
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

    public String getAdditionalComment() {
        return additionalComment;
    }

    public TaskDto setId(final int id) {
        this.id = id;
        return this;
    }
}
