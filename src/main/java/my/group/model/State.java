package my.group.model;

public enum State {
    PLANNED(),
    WORK_IN_PROGRESS(),
    NOTIFIED(),
    DONE(),
    CANCELLED();
//
//    private final String code;
//
//     State(String code) {
//        this.code = code;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public static State getState(String code) {
//        for (State state : State.values()) {
//            if (state.getCode().equals(code)) {
//                return state;
//            }
//        }
//        throw new IllegalArgumentException("Invalid State code: " + code);
//    }

}
