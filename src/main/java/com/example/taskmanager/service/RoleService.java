package com.example.taskmanager.service;

import com.example.taskmanager.model.Role;

public interface RoleService {
    Role save(Role role);

    Role findAllByRoleName(String roleName);
}
