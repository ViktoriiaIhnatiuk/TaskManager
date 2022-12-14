package com.example.taskmanager.repository;

import com.example.taskmanager.model.Status;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    @Query(value = "SELECT * "
            + "FROM statuses "
            + "WHERE status_name = ?;", nativeQuery = true)
    Optional<Status> findStatusByName(String name);
}
