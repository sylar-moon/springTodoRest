package my.group.controler;

import my.group.Exception.BadRequestException;
import my.group.Exception.NotFoundException;
import my.group.model.Response;
import my.group.model.State;
import my.group.model.Task;
import my.group.repository.TaskRepository;
import my.group.service.ResponseService;
import my.group.service.StatusValidateService;
import my.group.dto.TaskDto;
import my.group.service.TaskService;
import my.group.utility.MyLogger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("api/")
public class TaskController {
    private final Logger logger = new MyLogger().getLogger();

//    private final StatusValidateService validateService = new StatusValidateService();

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    TaskService taskService;

    @Autowired
    ResponseService responseService;
    @PostMapping("public/tasks")
    public ResponseEntity<String> addTask(@RequestBody @Valid TaskDto postRequest) {
        logger.info(("title = {}, description = {}, time end task = {}"),
                postRequest.getTitle(), postRequest.getDescription(), postRequest.getDateTimeToEndTask());

        Task task = new Task(postRequest.getTitle(), postRequest.getDescription(), postRequest.getDateTimeToEndTask());
        taskRepository.save(task);
        logger.info("Task is save");
        String message = messageSource.getMessage("task.add.success", null, LocaleContextHolder.getLocale());
        return ResponseEntity.ok(new Response(task, "api/public/tasks", HttpStatus.OK, message).toString());
    }

    @PatchMapping("public/tasks/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> updateTaskStatus(@PathVariable Long id, @RequestParam("status") State state) {
        String url = "api/public/tasks/" + id;
        Task task = taskService.updateTask(id,state,url);
        String message = messageSource.getMessage("task.update.success", null, LocaleContextHolder.getLocale());
        return ResponseEntity.ok(new Response(task, url, HttpStatus.OK, message).toString());

//
//        if (taskRepository.existsById(id)) {
//            task = taskRepository.findById(id).get();
//            if (validateService.isValidStatus(task, state)) {
//                task.setState(state);
//                taskRepository.save(task);
//
//                String message = messageSource.getMessage("task.update.success", null, LocaleContextHolder.getLocale());
//                return ResponseEntity.ok(new Response(task, url, HttpStatus.OK, message).toString());
//            } else {
//                String errorMessage = messageSource.getMessage("task.update.invalidStatus",
//                        new Object[]{task.getState(), state}, LocaleContextHolder.getLocale());
//                throw new BadRequestException(errorMessage,url,task);
//
////                Response badRequestResponse = new Response(task,
////                        url, HttpStatus.BAD_REQUEST, errorMessage);
////                return ResponseEntity.badRequest().body(badRequestResponse.toString());
//            }
//        } else {
//            String errorMessage = messageSource.getMessage("task.notFoundID", null,
//                    LocaleContextHolder.getLocale());
//
//            throw new NotFoundException(errorMessage,url);
//
////
////            Response notFoundResponse = new Response(null,
////                    url, HttpStatus.NOT_FOUND, errorMessage);
////            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse.toString());
//        }
    }

    @DeleteMapping("admin/tasks/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        String url = "api/admin/tasks/" + id;
        Task deletedTask =  taskService.deleteTask(id,url);

        String message = messageSource.getMessage("task.delete.success",null,LocaleContextHolder.getLocale());
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
        String message = responseService.getResponseMessage("task.get.success",null);
        return ResponseEntity.ok(new Response(task, url, HttpStatus.OK,message).toString());
    }

}