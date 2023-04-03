package io.github.mat3e.project;

import java.time.ZonedDateTime;

class ProjectDeadlineDto {
    private ZonedDateTime deadline;

    ZonedDateTime getDeadline() {
        return deadline;
    }

    ProjectDeadlineDto setDeadline(final ZonedDateTime deadline) {
        this.deadline = deadline;
        return this;
    }
}
