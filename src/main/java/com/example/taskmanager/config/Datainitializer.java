package com.example.taskmanager.config;

import com.example.taskmanager.model.Priority;
import com.example.taskmanager.model.Role;
import com.example.taskmanager.model.Status;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskList;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskListRepository;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.service.PriorityService;
import com.example.taskmanager.service.RoleService;
import com.example.taskmanager.service.StatusService;
import com.example.taskmanager.service.UserService;
import java.time.LocalDateTime;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class Datainitializer {
    private final StatusService statusService;
    private final UserService userService;
    private final RoleService roleService;
    private final TaskRepository taskRepository;
    private final PriorityService priorityService;
    private final TaskListRepository taskListRepository;

    public Datainitializer(StatusService statusService,
                           UserService userService,
                           RoleService roleService,
                           TaskRepository taskRepository,
                           PriorityService priorityService,
                           TaskListRepository taskListRepository) {
        this.statusService = statusService;
        this.userService = userService;
        this.roleService = roleService;
        this.taskRepository = taskRepository;
        this.priorityService = priorityService;
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

        User johnDoe = new User();
        johnDoe.setEmail("userjohn@i.ua");
        johnDoe.setName("John Doe");
        johnDoe.setPassword("user1234");
        johnDoe.setRoles(Set.of(userRole));
        userService.createUser(johnDoe);
        User janeDoe = new User();
        janeDoe.setEmail("userjane@i.ua");
        janeDoe.setName("Jane Doe");
        janeDoe.setPassword("user1234");
        janeDoe.setRoles(Set.of(userRole));
        userService.createUser(janeDoe);

        Status toDoStatus = new Status(Status.StatusName.TO_DO);
        statusService.createStatus(toDoStatus);
        Status inProgressStatus = new Status(Status.StatusName.IN_PROGRESS);
        statusService.createStatus(inProgressStatus);
        Status doneStatus = new Status(Status.StatusName.DONE);
        statusService.createStatus(doneStatus);
        Status terminatedStatus = new Status(Status.StatusName.TERMINATED);
        statusService.createStatus(terminatedStatus);

        Priority lowPriority = new Priority();
        lowPriority.setPriorityName(Priority.PriorityName.LOW);
        priorityService.createPriority(lowPriority);
        Priority mediumPriority = new Priority();
        mediumPriority.setPriorityName(Priority.PriorityName.MEDIUM);
        priorityService.createPriority(mediumPriority);
        Priority highPriority = new Priority();
        highPriority.setPriorityName(Priority.PriorityName.HIGH);
        priorityService.createPriority(highPriority);

        TaskList taskListOne = new TaskList();
        LocalDateTime deadline = LocalDateTime.now().plusDays(3);
        taskListOne.setName("John's taskList");
        taskListOne.setStatus(statusService.getStatusByName(Status.StatusName.TO_DO.name()));
        taskListOne.setUser(johnDoe);
        taskListOne.setDeadline(deadline);
        taskListOne.setPriority(highPriority);
        TaskList johnTaskList = taskListRepository.save(taskListOne);
        Task johnTask1 = new Task();
        johnTask1.setName("John's task1");
        johnTask1.setStatus(statusService.getStatusByName(Status.StatusName.TO_DO.name()));
        johnTask1.setTaskList(johnTaskList);
        johnTask1.setPriority(lowPriority);
        taskRepository.save(johnTask1);
        Task johnTask2 = new Task();
        johnTask2.setName("John's task2");
        johnTask2.setStatus(statusService.getStatusByName(Status.StatusName.TO_DO.name()));
        johnTask2.setTaskList(johnTaskList);
        johnTask2.setPriority(lowPriority);
        taskRepository.save(johnTask2);

        TaskList taskListTwo = new TaskList();
        LocalDateTime deadline2 = LocalDateTime.now().plusDays(1);
        taskListTwo.setName("Jane's taskList");
        taskListTwo.setStatus(statusService.getStatusByName(Status.StatusName.TO_DO.name()));
        taskListTwo.setUser(janeDoe);
        taskListTwo.setDeadline(deadline2);
        taskListTwo.setPriority(lowPriority);
        TaskList janeTaskList = taskListRepository.save(taskListTwo);
        Task janeTask1 = new Task();
        janeTask1.setName("Jane's task1");
        janeTask1.setStatus(statusService.getStatusByName(Status.StatusName.TO_DO.name()));
        janeTask1.setTaskList(janeTaskList);
        janeTask1.setPriority(highPriority);
        taskRepository.save(janeTask1);
        Task janeTask2 = new Task();
        janeTask2.setName("Jane's task2");
        janeTask2.setStatus(statusService.getStatusByName(Status.StatusName.TO_DO.name()));
        janeTask2.setTaskList(janeTaskList);
        janeTask2.setPriority(lowPriority);
        taskRepository.save(janeTask2);
    }
}
