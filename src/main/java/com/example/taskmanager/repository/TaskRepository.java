package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "SELECT* "
            + "FROM tasks t "
            + "JOIN task_lists tl "
            + "ON t.task_list_id = tl.id "
            + "JOIN users u "
            + "ON tl.user_id = u.id "
            + "WHERE u.email = ?;", nativeQuery = true)
    List<Task> getAllTasksByUserEmail(String userEmail);

    @Query(value = "SELECT * "
            + "FROM tasks t "
            + "JOIN task_lists tl "
            + "ON t.task_list_id = tl.id "
            + "WHERE t.id = ? "
            + "AND tl.user_id = ?;", nativeQuery = true)
    Optional<Task> findByIdAndUserName(Long taskId, Long userId);
}
