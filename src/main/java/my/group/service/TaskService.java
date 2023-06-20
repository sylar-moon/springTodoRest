package my.group.service;

import my.group.Exception.BadRequestException;
import my.group.Exception.NotFoundException;
import my.group.model.Response;
import my.group.model.State;
import my.group.model.Task;
import my.group.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class TaskService {
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
        throw new NotFoundException(errorMessage, url);
    }

    public Iterable<Task> getAllTask(String url) {
        Iterable<Task> tasks = taskRepository.findAll();
        if (tasks.iterator().hasNext()) {
            return tasks;
        }
        String errorMessage = messageSource.getMessage("task.notFoundID", null,
                LocaleContextHolder.getLocale());
        throw new NotFoundException(errorMessage, url);
    }

    public Task updateTask(Long id, State state, String url) {
        Task task = getTaskById(id, url);

        if (validateService.isValidStatus(task, state)) {
            task.setState(state);
            taskRepository.save(task);
        } else {
            String errorMessage = messageSource.getMessage("task.update.invalidStatus",
                    new Object[]{task.getState(), state}, LocaleContextHolder.getLocale());
            throw new BadRequestException(errorMessage, url, task);
        }
        return task;
    }
}


