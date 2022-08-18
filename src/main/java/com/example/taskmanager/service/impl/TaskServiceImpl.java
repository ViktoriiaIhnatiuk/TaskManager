package com.example.taskmanager.service.impl;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.service.TaskService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task creteTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(
                () -> new RuntimeException("Can't find task by id " + taskId)
        );
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task updateTaskById(Long taskId, Task task) {
        Task taskToUpdate = new Task();
        if (task.getName() != null) {
            taskToUpdate.setName(task.getName());
        }
        if (task.getStatus() != null) {
            taskToUpdate.setStatus(task.getStatus());
        }
        return creteTask(taskToUpdate);
    }

    @Override
    public Task deleteTaskById(Long taskId) {
        Task taskToDelete = getTaskById(taskId);
        taskRepository.delete(taskToDelete);
        return taskToDelete;
    }
}
