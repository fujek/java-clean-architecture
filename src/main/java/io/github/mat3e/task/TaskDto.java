package io.github.mat3e.task;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@JsonDeserialize(builder = TaskDto.Builder.class)
public class TaskDto {
    private final int id;
    @NotNull
    private final String description;
    private final boolean done;
    private final ZonedDateTime deadline;
    private final String additionalComment;

    private TaskDto(Builder builder) {
        id = builder.id;
        description = builder.description;
        done = builder.done;
        deadline = builder.deadline;
        additionalComment = builder.additionalComment;
    }

    public static Builder builder() {
        return new TaskDto.Builder();
    }

    Builder toBuilder() {
        return builder()
                .withAdditionalComment(this.additionalComment)
                .withDeadline(this.deadline)
                .withDescription(this.description)
                .withDone(this.done)
                .withId(this.id);
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

    @JsonPOJOBuilder
    public static class Builder {
        private int id;
        private String description;
        private boolean done;
        private ZonedDateTime deadline;
        private String additionalComment;

        private Builder() {
        }

        public Builder withId(final int id) {
            this.id = id;
            return this;
        }

        public Builder withDescription(final String description) {
            this.description = description;
            return this;
        }

        public Builder withDone(final boolean done) {
            this.done = done;
            return this;
        }

        public Builder withDeadline(final ZonedDateTime deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder withAdditionalComment(final String additionalComment) {
            this.additionalComment = additionalComment;
            return this;
        }

        public TaskDto build() {
            return new TaskDto(this);
        }
    }

}
