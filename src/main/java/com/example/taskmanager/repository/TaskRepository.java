package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "SELECT* " +
            "FROM tasks t " +
            "JOIN task_lists tl " +
            "ON t.task_list_id = tl.id " +
            "JOIN users u " +
            "ON tl.user_id = u.id " +
            "WHERE u.email = ?;", nativeQuery = true)
    List<Task> getAllTasksByUserEmail(String userEmail);
}
