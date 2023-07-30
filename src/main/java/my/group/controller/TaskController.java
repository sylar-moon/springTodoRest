package my.group.controller;

import my.group.dto.TaskDtoRequest;
import my.group.dto.TaskDtoResponse;
import my.group.model.State;
import my.group.model.Task;
import my.group.repository.TaskRepository;
import my.group.service.ResponseService;
import my.group.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/")
public class TaskController {

    TaskRepository taskRepository;

    TaskService taskService;

    ResponseService responseService;

    private final Logger logger = LoggerFactory.getLogger(TaskController.class);


    @Autowired
    public TaskController(TaskRepository taskRepository, TaskService taskService, ResponseService responseService) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
        this.responseService = responseService;
    }

    @PostMapping("public/tasks")
    @ResponseStatus(HttpStatus.OK)
    public TaskDtoResponse addTask(@RequestBody @Valid TaskDtoRequest taskDtoRequest) {
        Task task = taskService.saveTask(new Task(taskDtoRequest.getTitle(), taskDtoRequest.getDescription(), taskDtoRequest.getDateTimeToEndTask()));
        String message = responseService.getMessage("task.add.success", null);
        return taskService.getTaskDTOResponse(task, message, HttpStatus.OK);
    }

    @PatchMapping("public/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDtoResponse updateTaskStatus(@PathVariable Long id, @RequestParam("status") State state) {
        String url = "api/public/tasks/" + id;
        Task task = taskService.updateTaskState(id, state, url);
        String message = responseService.getMessage("task.update.success", null);
        return taskService.getTaskDTOResponse(task, message, HttpStatus.OK);
    }

    @DeleteMapping("admin/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDtoResponse deleteTask(@PathVariable Long id) {
        String url = "api/admin/tasks/" + id;
        Task task = taskService.deleteTask(id, url);
        String message = responseService.getMessage("task.delete.success", null);
        return taskService.getTaskDTOResponse(task, message, HttpStatus.OK);

    }

    @GetMapping("public/tasks")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskDtoResponse>> getAllTasks() {
        String url = "api/public/tasks/";
        String message = responseService.getMessage("task.get.success", null);
        List<TaskDtoResponse> taskDtoResponses = taskService.getListTaskResponse(taskService.getAllTask(url), message, HttpStatus.OK);
        return ResponseEntity.ok(taskDtoResponses);
    }

    @GetMapping("public/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskDtoResponse> getTaskById(@PathVariable Long id) {
        String url = "api/public/tasks/" + id;
        logger.info(url);
        Task task = taskService.getTaskById(id, url);
        logger.info(task.getTitle());
        String message = responseService.getMessage("task.get.success", null);
        logger.info(message);
        return ResponseEntity.ok(taskService.getTaskDTOResponse(task, message, HttpStatus.OK));
    }

    @GetMapping("admin/task/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskDtoResponse>> getAllTaskByPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        String url = "api/admin/tasks/";
        List<Task> task = taskService.getTasksByPage(page, size, url);
        String message = responseService.getMessage("task.get.success", null);
        List<TaskDtoResponse> taskDtoResponses = taskService.getListTaskResponse(task, message, HttpStatus.OK);
        return ResponseEntity.ok(taskDtoResponses);
    }
}