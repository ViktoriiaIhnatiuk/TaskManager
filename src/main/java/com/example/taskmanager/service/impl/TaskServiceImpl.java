package com.example.taskmanager.service.impl;

import com.example.taskmanager.model.Status;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskList;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.service.StatusService;
import com.example.taskmanager.service.TaskListServise;
import com.example.taskmanager.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final StatusService statusService;
    private final TaskListServise taskListServise;

    public TaskServiceImpl(TaskRepository taskRepository,
                           StatusService statusService,
                           TaskListServise taskListServise) {
        this.taskRepository = taskRepository;
        this.statusService = statusService;
        this.taskListServise = taskListServise;
    }

    @Transactional
    @Override
    public Task creteTask(Long taskListId, Task task) {
        TaskList taskListById = taskListServise.getTaskListById(taskListId);
        task.setTaskList(taskListById);
        if (task.getStatus() == null) {
            task.setStatus(statusService.getStatusByName(Status.StatusName.TO_DO));
        }
        Task newTask = taskRepository.save(task);
        if (newTask != null) {
            List<Task> tasks = taskListById.getTasks();
            tasks.add(newTask);
            taskListById.setTasks(tasks);
            taskListServise.createTaskList(taskListById);
        }
        return newTask;
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
        if (task.getStatus().getStatusName().equals(Status.StatusName.DONE)) {
            taskToUpdate.setDate(LocalDateTime.now());
        }
        return taskRepository.save(taskToUpdate);
    }

    @Override
    public Task deleteTaskById(Long taskId) {
        Task taskToDelete = getTaskById(taskId);
        taskRepository.delete(taskToDelete);
        return taskToDelete;
    }
}
