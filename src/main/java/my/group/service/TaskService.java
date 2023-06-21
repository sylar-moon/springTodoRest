package my.group.service;

import my.group.Exception.BadRequestException;
import my.group.Exception.NoContentException;
import my.group.Exception.NotFoundException;
import my.group.dto.TaskDto;
import my.group.model.Response;
import my.group.model.State;
import my.group.model.Task;
import my.group.repository.TaskRepository;
import my.group.utility.MyLogger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.naming.NotContextException;
import java.util.Iterator;

@Service
public class TaskService {
    private final Logger logger = new MyLogger().getLogger();

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    StatusValidateService validateService;


    public Task getTaskById(Long id, String url) {
        if (taskRepository.existsById(id)) {
            return taskRepository.findById(id).get();
        } else {
            String errorMessage = messageSource.getMessage("task.notFoundID", null,
                    LocaleContextHolder.getLocale());
            throw new NotFoundException(errorMessage, url);
        }
    }


    public Task deleteTask(Long id, String url) {
        Task task = getTaskById(id, url);
        if (task != null) {
            taskRepository.deleteById(id);
            return task;
        }
        String errorMessage = messageSource.getMessage("task.delete.notFoundID", null,
                LocaleContextHolder.getLocale());
        throw new NoContentException(errorMessage, url);
    }


    public Iterable<Task> getAllTask(String url) {
        Iterable<Task> tasks = taskRepository.findAll();
        if (tasks.iterator().hasNext()) {
            logger.info("Get all tasks from the database");
            return tasks;
        }
        String errorMessage = messageSource.getMessage("task.getAll.noContent", null,
                LocaleContextHolder.getLocale());
        logger.error(errorMessage);
        throw new NoContentException(errorMessage, url);
    }


    public Task updateTaskState(Long id, State state, String url) {
        Task task = getTaskById(id, url);
        State currentState = task.getState();
        if (validateService.isValidStatus(task, state)) {
            task.setState(state);
            taskRepository.save(task);
        } else {
            String errorMessage = messageSource.getMessage("task.update.invalidStatus",
                    new Object[]{task.getState(), state}, LocaleContextHolder.getLocale());
            throw new BadRequestException(errorMessage, url, task);
        }
        logger.info("Issue status updated from {} to {}",currentState,state);
        return task;
    }

    public Task saveTask(TaskDto taskDto) {
        Task task = new Task(taskDto.getTitle(), taskDto.getDescription(), taskDto.getDateTimeToEndTask());
        taskRepository.save(task);
        logger.info("Task saved: {}",task);
        return task;
    }
}


