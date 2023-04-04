package io.github.mat3e.task;

import io.github.mat3e.project.query.SimpleProjectQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class TaskFacade {
    private final TaskRepository taskRepository;

    TaskFacade(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public boolean existUndoneProjectTasks(int projectId) {
        return taskRepository.existsByDoneIsFalseAndProject_Id(projectId);
    }

    public List<TaskDto> saveAll(final List<TaskDto> tasks, SimpleProjectQuery project) {
        return taskRepository.saveAll(tasks.stream().map(t -> TaskFactory.from(t, project)).collect(toList())).stream().map(Task::toDto)
                .collect(toList());
    }

    TaskDto save(TaskDto toSave) {
        return taskRepository.save(
                taskRepository.findById(toSave.getId())
                        .map(existingTask -> {
                            if (existingTask.isDone() != toSave.isDone()) {
                                existingTask.setChangesCount(existingTask.getChangesCount() + 1);
                                existingTask.setDone(toSave.isDone());
                            }
                            existingTask.setAdditionalComment(toSave.getAdditionalComment());
                            existingTask.setDeadline(toSave.getDeadline());
                            existingTask.setDescription(toSave.getDescription());
                            return existingTask;
                        }).orElseGet(() -> {
                    var result = new Task(toSave.getDescription(), toSave.getDeadline(), null);
                    result.setAdditionalComment(toSave.getAdditionalComment());
                    return result;
                })
        ).toDto();
    }

    List<TaskDto> list() {
        return taskRepository.findAll().stream()
                .map(Task::toDto)
                .collect(toList());
    }

    List<TaskWithChangesDto> listWithChanges() {
        return taskRepository.findAll().stream()
                .map(TaskWithChangesDto::new)
                .collect(toList());
    }

    Optional<TaskDto> get(int id) {
        return taskRepository.findById(id).map(Task::toDto);
    }

    void delete(int id) {
        taskRepository.deleteById(id);
    }
}
