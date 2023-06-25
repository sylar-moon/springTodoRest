package my.group.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class TaskDto {
    @NotBlank
    private final String title;

    @NotBlank
    private final String description;

    private final LocalDateTime dateTimeToEndTask;


    public TaskDto(String title, String description,
                   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime dateTimeToEndTask) {
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
