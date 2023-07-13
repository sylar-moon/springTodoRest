package my.group.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table
public class Task {

    @Column
    @NotBlank
    private String title;

    @Column
    @NotBlank
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTimeToStartTask;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTimeToEndTask;

    @Column
    @Enumerated(EnumType.STRING)
    private State state;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;


    public Task(String title, String description, LocalDateTime dateTime) {
        this.title = title;
        this.description = description;
        this.dateTimeToEndTask = dateTime;
        this.state = State.PLANNED;
        this.dateTimeToStartTask = LocalDateTime.now();
    }

    public Task() {

    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public State getState() {
        return state;
    }

    public Task setState(State state) {
        this.state = state;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Task setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDateTime getDateTimeToEndTask() {
        return dateTimeToEndTask;
    }

    public Task setDateTimeToEndTask(LocalDateTime dateTime) {
        this.dateTimeToEndTask = dateTime;
        return this;
    }

    public LocalDateTime getDateTimeToStartTask() {
        return dateTimeToStartTask;
    }

    public Task setDateTimeToStartTask(LocalDateTime dateTimeToStartTask) {
        this.dateTimeToStartTask = dateTimeToStartTask;
        return this;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateTimeToStartTask=" + dateTimeToStartTask +
                ", dateTimeToEndTask=" + dateTimeToEndTask +
                ", state=" + state +
                ", id=" + id +
                '}';
    }
}
