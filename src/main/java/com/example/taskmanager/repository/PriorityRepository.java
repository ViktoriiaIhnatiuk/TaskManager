package com.example.taskmanager.repository;

import com.example.taskmanager.model.Priority;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
    @Query(value = "SELECT * "
            + "FROM priorities "
            + "WHERE priority_name = ?;", nativeQuery = true)
    Optional<Priority> findPriorityByName(String priorityName);
}
