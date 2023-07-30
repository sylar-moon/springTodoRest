package my.group.controller;

import my.group.dto.TaskDtoResponse;
import my.group.model.Task;
import my.group.repository.TaskRepository;
import my.group.service.ResponseService;
import my.group.service.TaskService;
import my.group.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TaskController.class)
class TaskControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    TaskService taskService;

    @MockBean
    UserService userService;

    @MockBean
    TaskRepository taskRepository;

    @MockBean
    ResponseService responseService;

    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void addTask() throws Exception {
        Task task = getTask();
        EntityModel<Task> entityModel = EntityModel.of(task);
        when(taskService.saveTask(task)).thenReturn(task);

        taskService.saveTask(task);
    }

    @Test
    void updateTaskStatus() {
    }

    @Test
    void deleteTask() {
    }

    @Test
    void getAllTasks() {
    }

    @Test
    void getTaskById() throws Exception {
        Task task = getTask();
        TaskDtoResponse taskDtoResponse = getTaskDTOResponse(task);
        when(taskService.getTaskById(1L,"api/public/tasks/1")).thenReturn(task);
        when(taskService.getTaskDTOResponse(task,"Завдання отримано успішно.",HttpStatus.OK)).thenReturn(taskDtoResponse);
        when(responseService.getMessage("task.get.success", null)).thenReturn("Завдання отримано успішно.");
        mockMvc.perform(get("/api/public/tasks/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Купити хліб"))
                .andExpect(jsonPath("$.description").value("Завтра купити хліб"))
                .andExpect(jsonPath("$.state").value("PLANNED"))
                .andExpect(jsonPath("$.httpStatus").value("OK"))
                .andExpect(jsonPath("$.message").value("Завдання отримано успішно."));
    }

    @NotNull
    private TaskDtoResponse getTaskDTOResponse(Task task) {
        return new TaskDtoResponse(task, "Завдання отримано успішно.", HttpStatus.OK, new ArrayList<>(Collections.singletonList(Link.of("http://localhost:5000/api/public/tasks"))));
    }

    @NotNull
    private Task getTask() {
        Task task = new Task("Купити хліб","Завтра купити хліб", LocalDateTime.now());
        task.setId(1L);
        return task;
    }

    @Test
    void getAllTaskByPage() {
    }
}