package my.group.service;

import my.group.exception.BadRequestException;
import my.group.exception.NoContentException;
import my.group.exception.NotFoundException;
import my.group.model.State;
import my.group.model.Task;
import my.group.repository.TaskRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class TaskServiceTest {
    static TaskService taskService;
    @Mock
    static TaskRepository repository;
    @Mock
    static MessageSource messageSource;
    @Mock
    static StatusValidateService validateService;
    @Mock
    static Page<Task> page;

    @BeforeAll
    static void init() {
        taskService = new TaskService();
        MockitoAnnotations.openMocks(new TaskServiceTest());
        taskService.setTaskRepository(repository);
        taskService.setMessageSource(messageSource);
        taskService.setValidateService(validateService);
    }

    @Test
    void saveTask() {
        Task savedTask = getTask();
        when(repository.save(savedTask)).thenReturn(savedTask);
        Task receivedTask = taskService.saveTask(savedTask);
        Assertions.assertNotNull(receivedTask);
        Assertions.assertEquals(savedTask, receivedTask);
        verify(repository, times(1)).save(savedTask);
        init();
    }


    @Test
    void getTaskById() {
        Task savedTask = getTask();
        Optional<Task> optional = Optional.of(savedTask);
        when(repository.findById(1L)).thenReturn(optional);
        when(repository.existsById(1L)).thenReturn(true);
        Task receivedTask = taskService.getTaskById(1L, "api/public/tasks");
        Assertions.assertNotNull(receivedTask);
        Assertions.assertEquals(savedTask, receivedTask);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).existsById(1L);
        init();
    }

    @Test
    void errorTestGetTaskById() {
        String errorMessage = "Can't find a task with this id";
        when(repository.existsById(1L)).thenReturn(false);
        when(messageSource.getMessage("task.notFoundID", null,
                LocaleContextHolder.getLocale())).thenReturn(errorMessage);

        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
            taskService.getTaskById(1L, "api/public/tasks");
        });

        Assertions.assertEquals(exception.getMessage(), errorMessage);

        verify(repository, times(1)).existsById(1L);
        verify(messageSource, times(1)).getMessage("task.notFoundID", null,
                LocaleContextHolder.getLocale());
        init();

    }

    @Test
    void deleteTask() {
        Task savedTask = getTask();
        Optional<Task> optional = Optional.of(savedTask);
        when(repository.findById(1L)).thenReturn(optional);
        when(repository.existsById(1L)).thenReturn(true);
        Task deleteTask = taskService.deleteTask(1L, "api/public/tasks");
        Assertions.assertNotNull(deleteTask);
        Assertions.assertEquals(savedTask, deleteTask);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).deleteById(1L);
        init();

    }


    @Test
    void errorTestDeleteTask() {
        String errorMessage = "Can't delete a task with this id";
        when(repository.existsById(1L)).thenReturn(false);
        when(messageSource.getMessage("task.notFoundID", null,
                LocaleContextHolder.getLocale())).thenReturn(errorMessage);

        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
            taskService.deleteTask(1L, "api/public/tasks");
        });

        Assertions.assertEquals(exception.getMessage(), errorMessage);

        verify(repository, times(1)).existsById(1L);
        verify(messageSource, times(1)).getMessage("task.notFoundID", null,
                LocaleContextHolder.getLocale());
        init();

    }

    @Test
    void getAllTask() {
        List<Task> tasks = Arrays.asList(
                getTask(), getTask()
        );
        Iterable<Task> iterable = mock(Iterable.class);
        when(iterable.iterator()).thenReturn(tasks.iterator());
        when(repository.findAll()).thenReturn(iterable);
        Iterable<Task> receivedTasks = taskService.getAllTask("api/public/tasks");
        Assertions.assertEquals(iterable, receivedTasks);
        verify(repository, times(1)).findAll();
        init();

    }


    @Test
    void errorTestGetAllTask() {
        List<Task> tasks = new ArrayList<>();
        String errorMessage = "Can't get all task";
        Iterable<Task> iterable = mock(Iterable.class);
        when(iterable.iterator()).thenReturn(tasks.iterator());

        when(messageSource.getMessage("task.getAll.noContent", null,
                LocaleContextHolder.getLocale())).thenReturn(errorMessage);

        Exception exception = Assertions.assertThrows(NoContentException.class, () -> {
            taskService.getAllTask("api/public/tasks");
        });

        Assertions.assertEquals(exception.getMessage(), errorMessage);

        verify(repository, times(1)).findAll();
        verify(messageSource, times(1)).getMessage("task.getAll.noContent", null,
                LocaleContextHolder.getLocale());
        init();

    }

    @Test
    void updateTaskState() {
        State updateState = State.WORK_IN_PROGRESS;
        Task savedTask = getTask();
        Optional<Task> optional = Optional.of(savedTask);
        when(repository.findById(1L)).thenReturn(optional);
        when(repository.existsById(1L)).thenReturn(true);
        when(validateService.isValidStatus(savedTask.getState(), updateState)).thenReturn(true);
        Task updateTask = taskService.updateTaskState(1L, updateState, "api/public/tasks");

        Assertions.assertEquals(savedTask.getTitle(), updateTask.getTitle());
        Assertions.assertEquals(savedTask.getDateTimeToEndTask(), updateTask.getDateTimeToEndTask());
        Assertions.assertEquals(savedTask.getDescription(), updateTask.getDescription());
        Assertions.assertEquals(savedTask.getId(), updateTask.getId());
        Assertions.assertEquals(updateTask.getState(), updateState);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).existsById(1L);
        verify(validateService, times(1)).isValidStatus(any(), any());
        init();

    }


    @Test
    void errorTestUpdateTaskStateById() {
        String errorMessageById = "Can't get task by id";

        State updateState = State.WORK_IN_PROGRESS;

        when(repository.existsById(1L)).thenReturn(false);
        when(messageSource.getMessage("task.notFoundID", null,
                LocaleContextHolder.getLocale())).thenReturn(errorMessageById);

        Exception exceptionById = Assertions.assertThrows(NotFoundException.class, () -> {
            taskService.updateTaskState(1L, updateState, "api/public/tasks");
        });
        Assertions.assertEquals(exceptionById.getMessage(), errorMessageById);

        verify(repository, times(1)).existsById(1L);
        verify(messageSource, times(1)).getMessage("task.notFoundID", null,
                LocaleContextHolder.getLocale());

        init();

    }


    @Test
    void errorTestUpdateTaskState() {
        String errorMessageUpdateTask = "Can't update state task";

        State updateState = State.WORK_IN_PROGRESS;
        Task savedTask = getTask();
        Optional<Task> optional = Optional.of(savedTask);

        when(repository.existsById(1L)).thenReturn(true);
        when(repository.findById(1L)).thenReturn(optional);

        when(validateService.isValidStatus(savedTask.getState(), updateState)).thenReturn(false);
        when(messageSource.getMessage("task.update.invalidStatus",
                new Object[]{savedTask.getState(), updateState}, LocaleContextHolder.getLocale())).thenReturn(errorMessageUpdateTask);

        BadRequestException exceptionByUpdateStatus = Assertions.assertThrows(BadRequestException.class, () -> {
            taskService.updateTaskState(1L, updateState, "api/public/tasks");
        });
        Assertions.assertEquals(exceptionByUpdateStatus.getErrorMessage(), errorMessageUpdateTask);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).findById(1L);
        verify(messageSource, times(1)).getMessage("task.update.invalidStatus",
                new Object[]{savedTask.getState(), updateState}, LocaleContextHolder.getLocale());

        init();

    }


    @Test
    void getTasksByPage() {
        Task savedTask = getTask();
        List<Task> tasks = Arrays.asList(savedTask, savedTask);
        when(page.getContent()).thenReturn(tasks);
        when(repository.findAll(PageRequest.of(0, 2))).thenReturn(page);
        List<Task> receivedTasks = taskService.getTasksByPage(0, 2, "api/public/tasks");
        Assertions.assertEquals(tasks, receivedTasks);

        verify(repository, times(1)).findAll(PageRequest.of(0, 2));
        verify(page, times(1)).getContent();

        init();
    }


    @Test
    void errorTestGetTasksByPage() {
        String errorMessage = "This page is empty";
        when(page.getContent()).thenReturn(new ArrayList<>());
        when(repository.findAll(PageRequest.of(0, 1))).thenReturn(page);
        when(messageSource.getMessage("task.getAll.noContent", null,
                LocaleContextHolder.getLocale())).thenReturn(errorMessage);
        NoContentException exception = Assertions.assertThrows(NoContentException.class, () -> {
            taskService.getTasksByPage(0, 1, "api/public/tasks");
        });
        Assertions.assertEquals(exception.getErrorMessage(), errorMessage);

        verify(repository, times(1)).findAll(PageRequest.of(0, 1));
        verify(page, times(1)).getContent();
        verify(messageSource, times(1)).getMessage("task.getAll.noContent", null,
                LocaleContextHolder.getLocale());

        init();
    }

    @NotNull
    private Task getTask() {
        return new Task("Купить молоко", "Завтра купить молоко", LocalDateTime.now());
    }
}