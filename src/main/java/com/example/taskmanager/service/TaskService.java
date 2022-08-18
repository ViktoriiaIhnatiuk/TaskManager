package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;

import java.util.List;

public interface TaskService {
    Task creteTask(Task task);
    Task getTaskById(Long taskId);
    List<Task> getAllTasks();
    Task updateTaskById(Long taskId, Task task);
    Task deleteTaskById(Long taskId);
}
