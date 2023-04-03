package io.github.mat3e;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.mat3e.model.Project;
import io.github.mat3e.model.Task;
import io.github.mat3e.model.TaskWithChanges;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = JavaCleanArchitectureApplication.class)
@AutoConfigureMockMvc
class JavaCleanArchitectureApplicationTests {

    private final MockMvc mvc;
    private String token;

    @Autowired
    JavaCleanArchitectureApplicationTests(final MockMvc mvc) {
        this.mvc = mvc;
    }

    @BeforeEach
    void authenticate() throws Exception {
        var mvcResult = mvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper().writeValueAsString(Map.of("username", "user", "password", "user"))))
                .andExpect(status().isOk())
                .andReturn();
        var result = objectMapper().readValue(mvcResult.getResponse().getContentAsString(), HashMap.class);
        token = result.get("token").toString();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void getProjects() throws Exception {
        var result = loadProjects();
        assertTrue(result.size() > 0);
    }

    @Test
    void getProjectWithId() throws Exception {
        var project = fetchProjectById(1);
        assertEquals(1, project.getId());
    }

    @Test
    void postProject() throws Exception {
        var projectsCount = loadProjects().size();
        mvc.perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getClass().getClassLoader().getResourceAsStream("postProject.json").readAllBytes())
                .header("authorization", "Bearer " + token))
                .andExpect(status().isCreated())
                .andReturn();
        assertEquals(projectsCount + 1, loadProjects().size());
    }

    @Test
    void putProject() throws Exception {
        var id = 1;
        mvc.perform(put("/projects/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getClass().getClassLoader().getResourceAsStream("putProjectsWithStepsWithIdRequest.json").readAllBytes())
                .header("authorization", "Bearer " + token))
                .andExpect(status().isNoContent())
                .andReturn();
        var project = fetchProjectById(id);
        assertNotNull(project);
        assertEquals(id, project.getId());
        var steps = project.getSteps();
        assertTrue(steps.stream().anyMatch(s -> s.getDescription().equals("Third")));
    }

    @Test
    void postProjectTasks() throws Exception {
        var initialTasks = getTasks();
        mvc.perform(post("/projects/1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getClass().getClassLoader().getResourceAsStream("postProjectTasksRequest.json").readAllBytes())
                .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();
        var project = fetchProjectById(1);
        var stepsCount = project.getSteps().size();
        List tasks = getTasks();
        assertEquals(initialTasks.size() + stepsCount, tasks.size());
    }

    @Test
    Task postTask() throws Exception {
        var initialTasks = getTasks();
        MvcResult mvcResult = mvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getClass().getClassLoader().getResourceAsStream("postTaskRequest.json").readAllBytes())
                .header("authorization", "Bearer " + token))
                .andExpect(status().isCreated())
                .andReturn();
        List tasks = getTasks();
        assertEquals(initialTasks.size() + 1, tasks.size());
        return objectMapper().readValue(mvcResult.getResponse().getContentAsString(), objectMapper().getTypeFactory().constructType(Task.class));
    }

    @Test
    void putTask() throws Exception {
        var task = postTask();
        var taskWithChanges = getTasksWithChanges().stream().filter(t -> t.getId() == task.getId()).findFirst().orElseThrow();
        mvc.perform(put("/tasks/{id}", task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getClass().getClassLoader().getResourceAsStream("putTaskRequest.json").readAllBytes())
                .header("authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
        var updatedTask = getTaskById(task.getId());
        assertEquals("Updated second", updatedTask.getDescription());
        var updatedTaskWithChanges = getTasksWithChanges().stream().filter(t -> t.getId() == task.getId()).findFirst().orElseThrow();
        assertEquals("Updated second", updatedTaskWithChanges.getDescription());
        assertEquals(taskWithChanges.getChangesCount() + 1, updatedTaskWithChanges.getChangesCount());
    }


    @Test
    void deleteTask() throws Exception {
        var task = postTask();
        var tasks = getTasks();
        deleteTask(task.getId());
        var currentTasks = getTasks();
        assertEquals(tasks.size() - 1, currentTasks.size());
    }

    private List<Project> loadProjects() throws Exception {
        var mvcResult = mvc.perform(get("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        return objectMapper().readValue(mvcResult.getResponse().getContentAsString(),
                objectMapper().getTypeFactory().constructCollectionType(ArrayList.class, Project.class));
    }

    private Project fetchProjectById(int id) throws Exception {
        var mvcResult = mvc.perform(get("/projects/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();
        return objectMapper().readValue(mvcResult.getResponse().getContentAsString(),
                objectMapper().getTypeFactory().constructType(Project.class));
    }

    List<Task> getTasks() throws Exception {
        var mvcResult = mvc.perform(get("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();
        return objectMapper().readValue(mvcResult.getResponse().getContentAsString(), objectMapper().getTypeFactory().constructCollectionType(ArrayList.class, Task.class));
    }

    Task getTaskById(int id) throws Exception {
        var mvcResult = mvc.perform(get("/tasks/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();
        return objectMapper().readValue(mvcResult.getResponse().getContentAsString(), objectMapper().getTypeFactory().constructType(Task.class));
    }

    List<TaskWithChanges> getTasksWithChanges() throws Exception {
        var mvcResult = mvc.perform(get("/tasks?changes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();
        return objectMapper().readValue(mvcResult.getResponse().getContentAsString(), objectMapper().getTypeFactory().constructCollectionType(ArrayList.class, TaskWithChanges.class));
    }

    void deleteTask(int id) throws Exception {
        mvc.perform(delete("/tasks/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    private ObjectMapper objectMapper() {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

}
