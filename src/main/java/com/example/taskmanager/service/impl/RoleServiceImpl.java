package com.example.taskmanager.service.impl;

import com.example.taskmanager.model.Role;
import com.example.taskmanager.repository.RoleRepository;
import com.example.taskmanager.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findAllByRoleName(String roleName) {
        return roleRepository.findAllByRoleName(roleName).orElseThrow(
                () -> new RuntimeException("Can't get role by role name: " + roleName));
    }
}
