package io.github.mat3e.project;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
class ProjectWarmup implements ApplicationListener<ContextRefreshedEvent> {

    private final ProjectRepository projectRepository;

    ProjectWarmup(final ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        if (projectRepository.count() == 0) {
            var project = new Project();
            project.setName("Example project");
            project.addStep(new ProjectStep("First", -3, project));
            project.addStep(new ProjectStep("Second", -2, project));
            project.addStep(new ProjectStep("Third", 0, project));
            projectRepository.save(project);
        }
    }
}
