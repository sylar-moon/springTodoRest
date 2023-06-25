package my.group.testing;

import my.group.model.State;
import my.group.model.Task;
import my.group.service.StatusValidateService;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

class StatusValidateServiceTest {

    static StatusValidateService statusService;

    @BeforeAll
    static void init() {
        statusService = new StatusValidateService();
    }

    private static Task buildTask(State state) {
        var task = new Task("Купить хлеб", "Зайти в магазин после работы и купить хлеб",
                LocalDateTime.of(2023, 6, 14, 20, 30, 0));
        task.setState(state.getCode());
        return task;
    }

    @Test
    void testPlaned() {
        Task task = buildTask(State.PLANNED);
        State state = State.valueOf(task.getState());
        Assertions.assertTrue(statusService.isValidStatus(state, State.WORK_IN_PROGRESS));
        Assertions.assertTrue(statusService.isValidStatus(state, State.CANCELLED));
        Assertions.assertFalse(statusService.isValidStatus(state, State.DONE));
        Assertions.assertFalse(statusService.isValidStatus(state, State.NOTIFIED));

    }

    @Test
    void testWorkInProgress() {
        Task task = buildTask(State.WORK_IN_PROGRESS);
        State state = State.valueOf(task.getState());
        Assertions.assertTrue(statusService.isValidStatus(state, State.NOTIFIED));
        Assertions.assertTrue(statusService.isValidStatus(state, State.CANCELLED));
        Assertions.assertFalse(statusService.isValidStatus(state, State.PLANNED));
        Assertions.assertFalse(statusService.isValidStatus(state, State.DONE));
    }

    @Test
    void testNotified() {
        Task task = buildTask(State.NOTIFIED);
        State state = State.valueOf(task.getState());
        Assertions.assertFalse(statusService.isValidStatus(state, State.PLANNED));
        Assertions.assertFalse(statusService.isValidStatus(state, State.WORK_IN_PROGRESS));
        Assertions.assertTrue(statusService.isValidStatus(state, State.CANCELLED));
        Assertions.assertTrue(statusService.isValidStatus(state, State.DONE));
    }

    @Test
    void testDone() {
        Task task = buildTask(State.DONE);
        State state = State.valueOf(task.getState());
        Assertions.assertTrue(statusService.isValidStatus(state, State.CANCELLED));
        Assertions.assertFalse(statusService.isValidStatus(state, State.PLANNED));
        Assertions.assertFalse(statusService.isValidStatus(state, State.WORK_IN_PROGRESS));
        Assertions.assertFalse(statusService.isValidStatus(state, State.NOTIFIED));
    }


    @Test
    void testCancelled() {
        Task task = buildTask(State.CANCELLED);
        State state = State.valueOf(task.getState());
        Assertions.assertFalse(statusService.isValidStatus(state, State.DONE));
        Assertions.assertFalse(statusService.isValidStatus(state, State.PLANNED));
        Assertions.assertFalse(statusService.isValidStatus(state, State.WORK_IN_PROGRESS));
        Assertions.assertFalse(statusService.isValidStatus(state, State.NOTIFIED));
    }
}