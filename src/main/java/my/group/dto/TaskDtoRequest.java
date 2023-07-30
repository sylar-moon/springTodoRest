package my.group.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import my.group.model.State;
import my.group.model.Task;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class TaskDtoRequest {

    @NotBlank
    private final String title;

    @NotBlank
    private final String description;

    private final LocalDateTime dateTimeToEndTask;

    public TaskDtoRequest(String title, String description,
                           @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  LocalDateTime dateTimeToEndTask) {
        this.title = title;
        this.description = description;
        this.dateTimeToEndTask = dateTimeToEndTask;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


    public LocalDateTime getDateTimeToEndTask() {
        return dateTimeToEndTask;
    }

    @Override
    public String toString() {
        return "TaskPostRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateTimeToEndTask=" + dateTimeToEndTask +
                '}';
    }


}
