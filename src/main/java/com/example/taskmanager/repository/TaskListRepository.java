package com.example.taskmanager.repository;

import com.example.taskmanager.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    @Query(value = "SELECT* " +
            "FROM task_lists t " +
            "JOIN users u " +
            "ON t.user_id = u.id " +
            "WHERE u.email = ?", nativeQuery = true)
    List<TaskList> getAllByUserName(String userName);
}
