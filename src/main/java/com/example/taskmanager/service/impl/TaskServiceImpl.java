package com.example.taskmanager.service.impl;

import com.example.taskmanager.model.Priority;
import com.example.taskmanager.model.Status;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskList;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskListRepository;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.service.PriorityService;
import com.example.taskmanager.service.StatusService;
import com.example.taskmanager.service.TaskListServise;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final StatusService statusService;
    private final TaskListServise taskListServise;
    private final UserService userService;
    private final TaskListRepository taskListRepository;
    private final PriorityService priorityService;

    public TaskServiceImpl(TaskRepository taskRepository,
                           StatusService statusService,
                           TaskListServise taskListServise,
                           UserService userService,
                           TaskListRepository taskListRepository,
                           PriorityService priorityService) {
        this.taskRepository = taskRepository;
        this.statusService = statusService;
        this.taskListServise = taskListServise;
        this.userService = userService;
        this.taskListRepository = taskListRepository;
        this.priorityService = priorityService;
    }

    @Transactional
    @Override
    public Task creteTask(Long taskListId, Task task) {
        TaskList taskListById = taskListServise.getTaskListById(taskListId);
        User currentUser = userService.getCurrentAuthenticatedUser();
        if (!userService.hasAdminRole(currentUser)
                && taskListById.getUser() != currentUser) {
            throw new RuntimeException("Please, choose a correct tasklist");
        }
        if (task.getStatus() == null) {
            task.setStatus(statusService.getStatusByName(String.valueOf(Status.StatusName.TO_DO)));
        }
        if (!taskListById.getStatus().getStatusName().equals(Status.StatusName.DONE)) {
            task.setTaskList(taskListById);
        } else {
            throw new RuntimeException("Can't add task to tasklist by id "
                    + taskListId + "because tasklist is already done");
        }
        if (task.getStatus().getStatusName().equals(Status.StatusName.DONE)) {
            task.setStatus(statusService.getStatusByName(Status.StatusName.IN_PROGRESS.name()));
            System.out.println("You can not create task with status \"Done\","
                    + "task has got status \"In progress\"");
        }
        if (task.getPriority() == null) {
            task.setPriority(priorityService
                    .getPriorityByName(Priority.PriorityName.MEDIUM.name()));
        }
        Task newTask = taskRepository.save(task);
        if (newTask != null) {
            List<Task> tasks = taskListById.getTasks();
            tasks.add(newTask);
            taskListById.setTasks(tasks);
            taskListServise.createTaskList(taskListById);
            taskListStatusUpdate(newTask);
        }
        return newTask;
    }

    @Override
    public Task getTaskById(Long taskId) {
        User currentUser = userService.getCurrentAuthenticatedUser();
        if (userService.hasAdminRole(currentUser)) {
            return taskRepository.findById(taskId).orElseThrow(
                    () -> new RuntimeException("Can't find task by id " + taskId));
        } else {
            return taskRepository.findByIdAndUserName(taskId,
                    currentUser.getId()).orElseThrow(
                        () -> new RuntimeException("Can't find task by id " + taskId));
        }
    }

    @Override
    public List<Task> getAllTasks() {
        User currentUser = userService.getCurrentAuthenticatedUser();
        if (userService.hasAdminRole(userService.getCurrentAuthenticatedUser())) {
            return taskRepository.findAll();
        } else {
            return taskRepository.getAllTasksByUserEmail(currentUser.getEmail());
        }
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

    @Transactional
    @Override
    public Task deleteTaskById(Long taskId) {
        Task taskToDelete = getTaskById(taskId);
        taskRepository.delete(taskToDelete);
        updateTaskListStatusAfterTaskDeleting(taskToDelete);
        return taskToDelete;
    }

    private void taskListStatusUpdate(Task taskToUpdate) {
        TaskList taskList = taskToUpdate.getTaskList();
        Long doneTasksCounter = taskList.getCounter();
        LocalDateTime deadLine = taskList.getDeadline();
        if (!taskList.getStatus().getStatusName().equals(taskToUpdate.getStatus().getStatusName())
                && taskToUpdate.getStatus().getId() > taskList.getStatus().getId()
                && !taskToUpdate.getStatus().getStatusName().equals(Status.StatusName.DONE)) {
            taskList.setStatus(taskToUpdate.getStatus());
        }

        if (!taskList.getStatus().getStatusName().equals(Status.StatusName.DONE)
                && !taskList.getStatus().getStatusName().equals(Status.StatusName.IN_PROGRESS)
                && taskToUpdate.getStatus().getStatusName().equals(Status.StatusName.DONE)) {
            taskList.setStatus(statusService.getStatusByName(Status.StatusName.IN_PROGRESS.name()));
        }

        if (taskToUpdate.getStatus().getStatusName().equals(Status.StatusName.DONE)) {
            LocalDateTime date = LocalDateTime.now();
            taskToUpdate.setDate(date);
            if (deadLine != null && date.isAfter(deadLine)) {
                taskToUpdate.setIsTerminated(true);
                taskList.setIsTerminated(true);
            }
            doneTasksCounter++;
            taskList.setCounter(doneTasksCounter);
        }

        if (doneTasksCounter == taskList.getTasks().size()
                && taskList.getDeadline() != null
                && (taskList.getTasks().stream()
                .map(Task::getDate)
                .anyMatch(e -> e.isAfter(taskList.getDeadline())))) {
            taskList.setIsTerminated(true);
        }
        if (doneTasksCounter == taskList.getTasks().size()) {
            taskList.setStatus(statusService.getStatusByName(Status.StatusName.DONE.name()));
        }
        taskListRepository.save(taskList);
    }

    private void updateTaskListStatusAfterTaskDeleting(Task taskToDelete) {
        TaskList taskList = taskToDelete.getTaskList();
        Long id = taskList.getId();
        long counter = taskList.getCounter();
        if (taskToDelete.getStatus().getStatusName().equals(Status.StatusName.DONE)) {
            taskList.setCounter(--counter);
        }
        if (!taskToDelete.getStatus().getStatusName().equals(Status.StatusName.TO_DO)
                && taskListServise.getTaskListById(id).getTasks().stream()
                .map(Task::getStatus)
                .allMatch(e -> e.getStatusName().equals(Status.StatusName.TO_DO))) {
            taskList.setStatus(statusService.getStatusByName(Status.StatusName.TO_DO.name()));
        }
        if (taskToDelete.getIsTerminated()
                && taskList.getTasks().stream()
                .noneMatch(Task::getIsTerminated)) {
            taskList.setIsTerminated(false);
        }
        taskListRepository.save(taskList);
    }
}
