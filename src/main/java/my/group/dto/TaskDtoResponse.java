package my.group.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import my.group.model.State;
import my.group.model.Task;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class TaskDtoResponse {
    @NotBlank
    private final Long id;

    @NotBlank
    private final String title;

    @NotBlank
    private final String description;

    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")

    private final LocalDateTime dateTimeToStartTask;

    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime dateTimeToEndTask;

    @NotBlank
    private final State state;

    @NotBlank
    private final List<Link> links;

    @NotBlank
    private final HttpStatus httpStatus;

    @NotBlank
    private final String message;

    public TaskDtoResponse(Task task, String message, HttpStatus httpStatus, List<Link> links) {
        this.dateTimeToStartTask = task.getDateTimeToStartTask();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.dateTimeToEndTask = task.getDateTimeToEndTask();
        this.message = message;
        this.links = links;
        this.state = task.getState();
        this.httpStatus = httpStatus;
        this.id=task.getId();
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTimeToStartTask() {
        return dateTimeToStartTask;
    }

    public State getState() {
        return state;
    }

    public List<Link> getLinks() {
        return links;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateTimeToEndTask() {
        return dateTimeToEndTask;
    }

    @Override
    public String toString() {
        return "TaskDtoRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateTimeToStartTask=" + dateTimeToStartTask +
                ", state=" + state +
                ", links=" + links +
                ", message='" + message + '\'' +
                ", dateTimeToEndTask=" + dateTimeToEndTask +
                '}';
    }
}
