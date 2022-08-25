package com.example.taskmanager.config;

import com.example.taskmanager.model.*;
import com.example.taskmanager.service.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Set;

@Component
public class Datainitializer {
    private final StatusService statusService;
    private final UserService userService;
    private final RoleService roleService;
    private final TaskListServise taskListServise;
    private final TaskService taskService;

    public Datainitializer(StatusService statusService,
                           UserService userService,
                           RoleService roleService,
                           TaskListServise taskListServise,
                           TaskService taskService) {
        this.statusService = statusService;
        this.userService = userService;
        this.roleService = roleService;
        this.taskListServise = taskListServise;
        this.taskService = taskService;
    }

    @PostConstruct
    public void injectStatuses() {
        Status toDoStatus = new Status(Status.StatusName.TO_DO);
        statusService.createStatus(toDoStatus);
        Status inProgressStatus = new Status(Status.StatusName.IN_PROGRESS);
        statusService.createStatus(inProgressStatus);
        Status doneStatus = new Status(Status.StatusName.DONE);
        statusService.createStatus(doneStatus);
        Status terminatedStatus = new Status(Status.StatusName.TERMINATED);
        statusService.createStatus(terminatedStatus);
//        TaskList taskListOne = new TaskList();
//        LocalDateTime deadline = LocalDateTime.parse("2022-08-23T18:25:00.000000");
//        taskListOne.setName("One");
//        taskListOne.setDeadline(deadline);
//        TaskList taskList = taskListServise.createTaskList(taskListOne);
//        Task taskOne = new Task();
//        taskOne.setName("1");
//        taskOne.setStatus(statusService.getStatusByName(Status.StatusName.IN_PROGRESS));
//        taskService.creteTask(taskList.getId(), taskOne);
//        Task taskTwo = new Task();
//        taskTwo.setName("2");
//        taskTwo.setStatus(statusService.getStatusByName(Status.StatusName.IN_PROGRESS));
//        taskService.creteTask(taskList.getId(), taskTwo);
    }

    @PostConstruct
    public void inject() {
        Role adminRole = new Role();
        adminRole.setRoleName(Role.RoleName.ADMIN);
        roleService.save(adminRole);
        Role userRole = new Role();
        userRole.setRoleName(Role.RoleName.USER);
        roleService.save(userRole);
        User admin = new User();
        admin.setEmail("admin@i.ua");
        admin.setPassword("admin1234");
        admin.setRoles(Set.of(adminRole));
        userService.createUser(admin);
        Role role = new Role();
        role.setRoleName(Role.RoleName.USER);
        roleService.save(role);
        User user = new User();
        user.setEmail("user@i.ua");
        user.setPassword("user1234");
        user.setRoles(Set.of(role));
        userService.createUser(user);
    }

}
