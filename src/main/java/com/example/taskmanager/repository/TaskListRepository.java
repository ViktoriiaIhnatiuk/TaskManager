package com.example.taskmanager.repository;

import com.example.taskmanager.model.TaskList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    @Query(value = "SELECT * "
            + "FROM task_lists t "
            + "JOIN users u "
            + "ON t.user_id = u.id "
            + "WHERE u.email = ?;", nativeQuery = true)
    List<TaskList> getAllByUserName(String userName);

    @Query(nativeQuery = true, value = "SELECT * "
            + "FROM task_lists t "
            + "WHERE id = ? "
            + "AND user_id = ?;")
    Optional<TaskList> findByIdAndUserName(Long taskListId, Long userId);
}
