package my.group.controler;

import my.group.model.Response;
import my.group.model.State;
import my.group.model.Task;
import my.group.repository.TaskRepository;
import my.group.service.ResponseService;
import my.group.dto.TaskDto;
import my.group.service.TaskService;
import my.group.utility.MyLogger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("api/")
public class TaskController {
    private final Logger logger = new MyLogger().getLogger();

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    ResponseService responseService;
    @PostMapping("public/tasks")
    public ResponseEntity<String> addTask(@RequestBody @Valid TaskDto taskDto) {
        Task task = taskService.saveTask(taskDto);
        String message = responseService.getMessage("task.add.success", null);
        return ResponseEntity.ok(new Response(task, "api/public/tasks", HttpStatus.OK, message).toString());
    }

    @PatchMapping("public/tasks/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> updateTaskStatus(@PathVariable Long id, @RequestParam("status") State state) {
        String url = "api/public/tasks/" + id;
        Task task = taskService.updateTaskState(id,state,url);
        String message = responseService.getMessage("task.update.success", null);
        return ResponseEntity.ok(new Response(task, url, HttpStatus.OK, message).toString());
    }

    @DeleteMapping("admin/tasks/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        String url = "api/admin/tasks/" + id;
        Task deletedTask =  taskService.deleteTask(id,url);
        String message = responseService.getMessage("task.delete.success",null);
        return ResponseEntity.ok(new Response(deletedTask, url, HttpStatus.OK,message).toString());
    }

    @GetMapping("public/tasks")
    public ResponseEntity<String> getAllTasks() {
        String url = "api/public/tasks/";
        List<Response> responses = responseService.getListResponses(taskService.getAllTask(url),url);
        return ResponseEntity.ok(responses.toString());
    }

    @GetMapping("public/tasks/{id}")
    public ResponseEntity<String> getTaskById(@PathVariable Long id){
        String url = "api/admin/tasks/" + id;
        Task task = taskService.getTaskById(id,url);
        String message = responseService.getMessage("task.get.success",null);
        return ResponseEntity.ok(new Response(task, url, HttpStatus.OK,message).toString());
    }

}