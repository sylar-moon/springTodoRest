package my.group.service;

import my.group.model.State;
import my.group.model.Task;
import org.springframework.stereotype.Service;

@Service
public class StatusValidateService {

    public boolean isValidStatus(Task task, State status) {
        int currentTaskState = task.getState().ordinal();
        int newTaskState = status.ordinal();
        if (newTaskState == State.CANCELLED.ordinal()) {
            return true;
        }
        return currentTaskState + 1 == newTaskState;
    }

}
