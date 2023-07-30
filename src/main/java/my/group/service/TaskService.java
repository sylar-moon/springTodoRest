package my.group.service;

import my.group.assembler.TaskModelAssembler;
import my.group.dto.TaskDtoResponse;
import my.group.exception.BadRequestException;
import my.group.exception.NoContentException;
import my.group.exception.NotFoundException;
import my.group.model.State;
import my.group.model.Task;
import my.group.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TaskService {
    private final Logger logger = LoggerFactory.getLogger(TaskService.class);

    TaskModelAssembler taskModelAssembler;

    TaskRepository taskRepository;

    MessageSource messageSource;

    StatusValidateService validateService;


    @Autowired
    public TaskService setTaskModelAssembler(TaskModelAssembler taskModelAssembler) {
        this.taskModelAssembler = taskModelAssembler;
        return this;
    }

    @Autowired
    public TaskService setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        return this;
    }

    @Autowired
    public TaskService setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
        return this;
    }

    @Autowired
    public TaskService setValidateService(StatusValidateService validateService) {
        this.validateService = validateService;
        return this;
    }

    public TaskDtoResponse getTaskDTOResponse(Task task, String message, HttpStatus httpStatus) {
        EntityModel<Task> entityModel = taskModelAssembler.toModel(task);
        logger.info("getTaskDtoEntityModel {}", entityModel.toString());
        return new TaskDtoResponse(task,message,httpStatus,entityModel.getLinks().toList());
    }

    public List<TaskDtoResponse> getListTaskResponse(Iterable<Task> allTask,String message,HttpStatus httpStatus) {

        return StreamSupport.stream(allTask.spliterator(), false)
                .map(task ->
                        new TaskDtoResponse(task,message,httpStatus,getTaskDtoEntityModel(task).getLinks().toList()))
                .collect(Collectors.toList());
    }

    public EntityModel<Task> getTaskDtoEntityModel(Task task) {
        EntityModel<Task> entityModel = taskModelAssembler.toModel(task);
        logger.info("getTaskDtoEntity {}", entityModel.toString());
        return taskModelAssembler.toModel(task);
    }

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


    public Task updateTaskState(Long id, State updateState, String url) {
        Task task = getTaskById(id, url);
        State currentState = task.getState();
        if (validateService.isValidStatus(currentState, updateState)) {
            task.setState(updateState);
            taskRepository.save(task);
        } else {
            String errorMessage = messageSource.getMessage("task.update.invalidStatus",
                    new Object[]{currentState, updateState}, LocaleContextHolder.getLocale());
            throw new BadRequestException(errorMessage, url, task);
        }
        logger.info("Issue status updated from {} to {}", currentState, updateState);
        return task;
    }

    public Task saveTask(Task task) {
        taskRepository.save(task);
        logger.info("Task saved: {}", task);
        return task;
    }

    public List<Task> getTasksByPage(int page, int size, String url) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> taskPage = taskRepository.findAll(pageable);
        List<Task> result = taskPage.getContent();
        if (result.isEmpty()) {
            String errorMessage = messageSource.getMessage("task.getAll.noContent", null,
                    LocaleContextHolder.getLocale());
            logger.error(errorMessage);
            throw new NoContentException(errorMessage, url);
        }
        return result;
    }
}


