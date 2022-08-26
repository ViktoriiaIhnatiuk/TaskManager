package com.example.taskmanager.service.impl;

import com.example.taskmanager.model.Role;
import com.example.taskmanager.model.Status;
import com.example.taskmanager.model.TaskList;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskListRepository;
import com.example.taskmanager.service.RoleService;
import com.example.taskmanager.service.StatusService;
import com.example.taskmanager.service.TaskListServise;
import com.example.taskmanager.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskListServiceImpl implements TaskListServise {
    private final TaskListRepository taskListRepository;
    private final StatusService statusService;
    private final UserService userService;
    private final RoleService roleService;

    public TaskListServiceImpl(TaskListRepository taskListRepository,
                               StatusService statusService,
                               UserService userService,
                               RoleService roleService) {
        this.taskListRepository = taskListRepository;
        this.statusService = statusService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if (taskList.getStatus() == null) {
            taskList.setStatus(statusService.getStatusByName(Status.StatusName.TO_DO));
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        taskList.setUser(userService.getUserByEmail(currentPrincipalName));
        return taskListRepository.save(taskList);
    }

    @Override
    public TaskList getTaskListById(Long taskListId) {
        return taskListRepository.findById(taskListId).orElseThrow(
                () -> new RuntimeException("Can't find taskList by id " + taskListId));
    }

    @Override
    public List<TaskList> getAllTaskLists() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User current = userService.getUserByEmail(currentPrincipalName);
        if (current.getRoles().stream()
                .map(e -> e.getRoleName())
                .filter(e -> e.equals(Role.RoleName.ADMIN))
                .count() > 0){
            return taskListRepository.findAll();
        } else {
            return taskListRepository.getAllByUserName(currentPrincipalName);
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
        createTaskList(taskListToUpdate);
        return taskListToUpdate;
    }

    @Override
    public TaskList deleteTaskList(Long taskListId) {
        TaskList taskListToDelete = getTaskListById(taskListId);
        taskListRepository.delete(taskListToDelete);
        return taskListToDelete;
    }
}
