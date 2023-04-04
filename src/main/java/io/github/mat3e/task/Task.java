package io.github.mat3e.task;

import io.github.mat3e.project.Project;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    @NotNull
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private int changesCount;
    private String additionalComment;
    @ManyToOne
    @JoinColumn(name = "source_id")
    private Project project;

    @PersistenceConstructor
    Task() {
    }

    public Task(@NotNull String description, ZonedDateTime deadline, Project project) {
        this.description = description;
        this.deadline = deadline;
        this.project = project;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    void setDone(boolean done) {
        this.done = done;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    void setDeadline(ZonedDateTime deadline) {
        this.deadline = deadline;
    }

    public int getChangesCount() {
        return changesCount;
    }

    void setChangesCount(int changesCount) {
        this.changesCount = changesCount;
    }

    public String getAdditionalComment() {
        return additionalComment;
    }

    void setAdditionalComment(String additionalComment) {
        this.additionalComment = additionalComment;
    }

    public Project getProject() {
        return project;
    }
}
