package com.example.taskmanager.config;

import com.example.taskmanager.model.Role;
import com.example.taskmanager.model.Status;
import com.example.taskmanager.model.User;
import com.example.taskmanager.service.RoleService;
import com.example.taskmanager.service.StatusService;
import com.example.taskmanager.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class Datainitializer {
    private final StatusService statusService;
    private final UserService userService;
    private final RoleService roleService;

    public Datainitializer(StatusService statusService,
                           UserService userService,
                           RoleService roleService) {
        this.statusService = statusService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void injectStatuses() {
        Status toDoStatus = new Status(Status.StatusName.TO_DO);
        statusService.createStatus(toDoStatus);
        Status inProgressStatus = new Status(Status.StatusName.IN_PROGRESS);
        statusService.createStatus(inProgressStatus);
        Status doneStatus = new Status(Status.StatusName.DONE);
        statusService.createStatus(doneStatus);
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
