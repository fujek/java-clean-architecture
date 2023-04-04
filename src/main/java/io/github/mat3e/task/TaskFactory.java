package io.github.mat3e.task;

import io.github.mat3e.project.Project;

class TaskFactory {
    static Task from(TaskDto taskDto, Project project) {
        var task = new Task(taskDto.getDescription(), taskDto.getDeadline(), project);
        task.setId(taskDto.getId());
        task.setAdditionalComment(taskDto.getAdditionalComment());
        task.setDescription(taskDto.getDescription());
        task.setDone(taskDto.isDone());
        return task;
    }
}
