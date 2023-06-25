package my.group.service;

import my.group.model.State;
import org.springframework.stereotype.Service;

@Service
public class StatusValidateService {

    public boolean isValidStatus(State currentState, State updateState) {
        switch (currentState) {
            case PLANNED:
                return updateState == State.WORK_IN_PROGRESS || updateState == State.CANCELLED;
            case WORK_IN_PROGRESS:
                return updateState == State.NOTIFIED || updateState == State.CANCELLED;
            case NOTIFIED:
                return updateState == State.DONE || updateState == State.CANCELLED;
            case DONE:
                return updateState == State.CANCELLED;
            default:
                return false;
        }
    }
}
