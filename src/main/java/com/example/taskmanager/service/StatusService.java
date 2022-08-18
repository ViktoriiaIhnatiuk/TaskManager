package com.example.taskmanager.service;

import com.example.taskmanager.model.Status;

import java.util.List;

public interface StatusService {
    Status createStatus(Status status);
    Status getStatusById(Long statusId);
    List<Status> getAllStatuses();
    Status updateStatusById(Long statusId, Status status);
    Status deleteStatusById(Long statusId);
}
