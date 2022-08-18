package com.example.taskmanager.repository;

import com.example.taskmanager.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
