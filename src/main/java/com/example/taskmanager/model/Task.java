package com.example.taskmanager.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Status status;
    private LocalDateTime date;
    @ManyToOne
    private TaskList taskList;
    @ManyToOne
    private Priority priority;
    private boolean isTerminated;

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean getIsTerminated() {
        return isTerminated;
    }

    public void setIsTerminated(boolean terminated) {
        isTerminated = terminated;
    }

    @Override
    public String toString() {
        return "Task{"
                + "id=" + id
                + ", name='" + name
                + ", status=" + status
                + ", date=" + date
                + ", taskList=" + taskList
                + ", priority=" + priority
                + ", isTerminated=" + isTerminated
                + '}';
    }
}
