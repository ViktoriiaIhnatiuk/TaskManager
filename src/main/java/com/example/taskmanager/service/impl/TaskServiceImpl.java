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
        if (task.getStatus() == null) {
            task.setStatus(statusService.getStatusByName(Status.StatusName.TO_DO));
        }
        if (!taskListById.getStatus().getStatusName().equals(Status.StatusName.DONE)) {
            task.setTaskList(taskListById);
        } else {
            throw new RuntimeException("Can't add task to tasklist by id "
                    + taskListId + "because tasklist is already done");
        }
        if (task.getStatus().getStatusName().equals(Status.StatusName.DONE)) {
            task.setStatus(statusService.getStatusByName(Status.StatusName.IN_PROGRESS));
            System.out.println("You can not create task with status \"Done\"," +
                    "task has got status \"In progress\"");
        }
        Task newTask = taskRepository.save(task);
        if (newTask != null) {
            List<Task> tasks = taskListById.getTasks();
            tasks.add(newTask);
            taskListById.setTasks(tasks);
            taskListServise.createTaskList(taskListById);
        }

        taskListStatusUpdate(newTask);
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

    @Transactional
    @Override
    public Task updateTaskById(Long taskId, Task task) {
        Task taskToUpdate = getTaskById(taskId);
        if (task.getName() != null) {
            taskToUpdate.setName(task.getName());
        }
        if (task.getStatus() != null) {
            taskToUpdate.setStatus(task.getStatus());
        }
        taskListStatusUpdate(taskToUpdate);
        return taskRepository.save(taskToUpdate);
    }

    @Override
    public Task deleteTaskById(Long taskId) {
        Task taskToDelete = getTaskById(taskId);
        taskRepository.delete(taskToDelete);
        return taskToDelete;
    }

    private void taskListStatusUpdate(Task taskToUpdate) {
        TaskList taskList = taskToUpdate.getTaskList();
        Long doneTasksCounter = taskList.getCounter();
        if (!taskList.getStatus().getStatusName().equals(taskToUpdate.getStatus().getStatusName())
        && taskToUpdate.getStatus().getId() > taskList.getStatus().getId()
                && !taskToUpdate.getStatus().getStatusName().equals(Status.StatusName.DONE)) {
            taskList.setStatus(taskToUpdate.getStatus());
        }

        if (!taskList.getStatus().getStatusName().equals(Status.StatusName.DONE)
                && !taskList.getStatus().getStatusName().equals(Status.StatusName.IN_PROGRESS)
                && taskToUpdate.getStatus().getStatusName().equals(Status.StatusName.DONE)) {
            taskList.setStatus(statusService.getStatusByName(Status.StatusName.IN_PROGRESS));
        }

        if (taskToUpdate.getStatus().getStatusName().equals(Status.StatusName.DONE)) {
            taskToUpdate.setDate(LocalDateTime.now());
            doneTasksCounter++;
            taskList.setCounter(doneTasksCounter);
        }

        if(doneTasksCounter == taskList.getTasks().size()
        && ! (taskList.getTasks().stream()
                .map(Task::getDate)
                .filter(e -> e.isAfter(taskList.getDeadline()))
                .count() > 0)) {
            taskList.setStatus(statusService.getStatusByName(Status.StatusName.DONE));

        } else if (doneTasksCounter == taskList.getTasks().size()
                && (taskList.getTasks().stream()
                .map(Task::getDate)
                .filter(e -> e.isAfter(taskList.getDeadline()))
                .count() > 0)){
            taskList.setStatus(statusService.getStatusByName(Status.StatusName.TERMINATED));
        }
        taskListServise.createTaskList(taskList);
    }
}
