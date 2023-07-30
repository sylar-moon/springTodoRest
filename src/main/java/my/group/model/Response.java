package my.group.model;

import org.springframework.http.HttpStatus;

public class Response {

    private String task;
    private HttpStatus status;
    private String message;

    public Response() {
    }

    public Response(Object obj, HttpStatus status, String message) {
        this.task =obj.toString();
        this.status = status;
        this.message = message;
    }


    public Response( HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getTask() {
        return task;
    }

    public Response setTask(String task) {
        this.task = task;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Response setMessage(String message) {
        this.message = message;
        return this;
    }

    public Response setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Response{" +
                "obj='" + task + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
