package com.example.taskmanager.config;

import com.example.taskmanager.model.*;
import com.example.taskmanager.repository.TaskListRepository;
import com.example.taskmanager.repository.TaskRepository;
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
    private final TaskRepository taskRepository;
    private final TaskService taskService;
    private final TaskListRepository taskListRepository;

    public Datainitializer(StatusService statusService,
                           UserService userService,
                           RoleService roleService,
                           TaskRepository taskRepository,
                           TaskService taskService,
                           TaskListRepository taskListRepository) {
        this.statusService = statusService;
        this.userService = userService;
        this.roleService = roleService;
        this.taskRepository = taskRepository;
        this.taskService = taskService;
        this.taskListRepository = taskListRepository;
    }

    @PostConstruct
    public void injectStatuses() {
        Role adminRole = new Role();
        adminRole.setRoleName(Role.RoleName.ADMIN);
        roleService.save(adminRole);
        Role userRole = new Role();
        userRole.setRoleName(Role.RoleName.USER);
        roleService.save(userRole);

        User admin = new User();
        admin.setEmail("admin@i.ua");
        admin.setPassword("admin1234");
        admin.setName("Mary Sue");
        admin.setRoles(Set.of(adminRole));
        userService.createUser(admin);
        Role role = new Role();
        role.setRoleName(Role.RoleName.USER);
        roleService.save(role);
        User johnDoe = new User();
        johnDoe.setEmail("userjohn@i.ua");
        johnDoe.setName("John Doe");
        johnDoe.setPassword("user1234");
        johnDoe.setRoles(Set.of(role));
        userService.createUser(johnDoe);
        User janeDoe = new User();
        janeDoe.setEmail("userjane@i.ua");
        janeDoe.setName("Jane Doe");
        janeDoe.setPassword("user1234");
        janeDoe.setRoles(Set.of(role));
        userService.createUser(janeDoe);

        Status toDoStatus = new Status(Status.StatusName.TO_DO);
        statusService.createStatus(toDoStatus);
        Status inProgressStatus = new Status(Status.StatusName.IN_PROGRESS);
        statusService.createStatus(inProgressStatus);
        Status doneStatus = new Status(Status.StatusName.DONE);
        statusService.createStatus(doneStatus);
        Status terminatedStatus = new Status(Status.StatusName.TERMINATED);
        statusService.createStatus(terminatedStatus);

        TaskList taskListOne = new TaskList();
        LocalDateTime deadline = LocalDateTime.parse("2022-08-23T18:25:00.000000");
        taskListOne.setName("One");
        taskListOne.setUser(johnDoe);
        taskListOne.setDeadline(deadline);
        TaskList johnTaskList = taskListRepository.save(taskListOne);
        Task taskOne = new Task();
        taskOne.setName("1");
        taskOne.setStatus(statusService.getStatusByName(Status.StatusName.IN_PROGRESS));
        taskOne.setTaskList(johnTaskList);
        taskRepository.save(taskOne);
        Task taskTwo = new Task();
        taskTwo.setName("2");
        taskTwo.setStatus(statusService.getStatusByName(Status.StatusName.IN_PROGRESS));
        taskTwo.setTaskList(johnTaskList);
        taskRepository.save(taskTwo);
//
//        LocalDateTime deadline2 = LocalDateTime.parse("2022-10-23T18:25:00.000000");
//        taskListOne.setName("Two");
//        taskListOne.setUser(johnDoe);
//        taskListOne.setDeadline(deadline2);
//        TaskList johnTaskListTwo = taskListRepository.save(taskListOne);
//        taskOne.setName("1");
//        taskOne.setStatus(statusService.getStatusByName(Status.StatusName.IN_PROGRESS));
//        taskService.creteTask(johnTaskListTwo.getId(), taskOne);
//        taskTwo.setName("2");
//        taskTwo.setStatus(statusService.getStatusByName(Status.StatusName.IN_PROGRESS));
//        taskTwo.setTaskList(johnTaskList);
//        taskRepository.save(taskTwo);
    }
}
