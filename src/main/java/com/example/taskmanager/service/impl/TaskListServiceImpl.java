package com.example.taskmanager.service.impl;

import com.example.taskmanager.model.Priority;
import com.example.taskmanager.model.Status;
import com.example.taskmanager.model.TaskList;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskListRepository;
import com.example.taskmanager.service.PriorityService;
import com.example.taskmanager.service.StatusService;
import com.example.taskmanager.service.TaskListServise;
import com.example.taskmanager.service.UserService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TaskListServiceImpl implements TaskListServise {
    private final TaskListRepository taskListRepository;
    private final StatusService statusService;
    private final UserService userService;

    private final PriorityService priorityService;

    public TaskListServiceImpl(TaskListRepository taskListRepository,
                               StatusService statusService,
                               UserService userService,
                               PriorityService priorityService) {
        this.taskListRepository = taskListRepository;
        this.statusService = statusService;
        this.userService = userService;
        this.priorityService = priorityService;
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        User currentUser = userService.getCurrentAuthenticatedUser();
        if (taskList.getStatus() == null) {
            taskList.setStatus(statusService
                    .getStatusByName(String.valueOf(Status.StatusName.TO_DO)));
        }
        if (taskList.getPriority() == null) {
            taskList.setPriority(priorityService
                    .getPriorityByName(Priority.PriorityName.MEDIUM.name()));
        }
        if (!userService.hasAdminRole(currentUser)) {
            taskList.setUser(currentUser);
        }
        return taskListRepository.save(taskList);
    }

    @Override
    public TaskList getTaskListById(Long taskListId) {
        User currentUser = userService.getCurrentAuthenticatedUser();
        if (userService.hasAdminRole(currentUser)) {
            return taskListRepository.findById(taskListId).orElseThrow(
                    () -> new RuntimeException("Can't find taskList by id " + taskListId));
        } else {
            return taskListRepository.findByIdAndUserName(taskListId,
                    currentUser.getId()).orElseThrow(
                        () -> new RuntimeException("Can't find taskList by id " + taskListId));
        }
    }

    @Override
    public List<TaskList> getAllTaskLists() {
        User currentUser = userService.getCurrentAuthenticatedUser();
        if (userService.hasAdminRole(userService.getCurrentAuthenticatedUser())) {
            return taskListRepository.findAll();
        } else {
            return taskListRepository.getAllByUserName(currentUser.getEmail());
        }
    }

    @Override
    public TaskList updateTaskListById(Long taskListId, TaskList taskList) {
        TaskList taskListToUpdate = getTaskListById(taskListId);
        if (taskList.getName() != null) {
            taskListToUpdate.setName(taskList.getName());
        }
        if (taskList.getDeadline() != null) {
            taskListToUpdate.setDeadline(taskList.getDeadline());
        }
        if (taskList.getTasks() != null) {
            taskListToUpdate.setTasks(taskList.getTasks());
        }
        if (taskList.getStatus() != null) {
            taskListToUpdate.setStatus(taskList.getStatus());
        }
        return taskListRepository.save(taskListToUpdate);
    }

    @Override
    public TaskList deleteTaskList(Long taskListId) {
        TaskList taskListToDelete = getTaskListById(taskListId);
        taskListRepository.delete(taskListToDelete);
        return taskListToDelete;
    }
}
