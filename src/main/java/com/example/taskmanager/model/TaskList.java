package com.example.taskmanager.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "task_lists")
public class TaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Status status;
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "taskList")
    private List<Task> tasks;
    private LocalDateTime deadline;
    @NotNull
    @ManyToOne
    private User user;
    @ManyToOne
    private Priority priority;
    private boolean isTerminated;
    private long counter;

    public TaskList() {
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "TaskList{"
                + "id=" + id
                + ", name='" + name
                + ", status=" + status
                + ", tasks=" + tasks
                + ", deadline=" + deadline
                + ", user=" + user
                + ", priority=" + priority
                + ", isTerminated=" + isTerminated
                + ", counter=" + counter
                + '}';
    }
}
