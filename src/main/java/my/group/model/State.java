package my.group.model;

public enum State {
    PLANNED("P"),
    WORK_IN_PROGRESS("W"),
    NOTIFIED("N"),
    DONE("D"),
    CANCELLED("C");

    private final String code;

    State(String code) {
        this.code = code;
    }
    //PAGING , exception , dto , state , get id
}
