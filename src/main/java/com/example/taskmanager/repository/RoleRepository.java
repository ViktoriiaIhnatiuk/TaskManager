package com.example.taskmanager.repository;

import com.example.taskmanager.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findAllByRoleName(String roleName);
}
