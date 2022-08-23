package com.example.taskmanager.service.impl;

import com.example.taskmanager.model.Role;
import com.example.taskmanager.model.User;
import com.example.taskmanager.service.AuthenticationService;
import com.example.taskmanager.service.RoleService;
import com.example.taskmanager.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final RoleService roleService;

    public AuthenticationServiceImpl(UserService userService,
                                     RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(Set.of(roleService.findAllByRoleName(Role.RoleName.USER.name())));
        userService.createUser(user);
        return user;
    }
}
