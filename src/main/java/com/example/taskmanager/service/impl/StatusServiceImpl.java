package com.example.taskmanager.service.impl;

import com.example.taskmanager.model.Status;
import com.example.taskmanager.repository.StatusRepository;
import com.example.taskmanager.service.StatusService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public Status createStatus(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public Status getStatusByName(String statusName) {
        return statusRepository.findStatusByName(statusName).orElseThrow(
                () -> new RuntimeException("Can't find status by statusName " + statusName));
    }

    @Override
    public Status getStatusById(Long statusId) {
        return statusRepository.findById(statusId).orElseThrow(
                () -> new RuntimeException("Can't find status by id " + statusId));
    }

    @Override
    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    @Override
    public Status updateStatusById(Long statusId, Status status) {
        Status statusToUpdate = getStatusById(statusId);
        if (status.getStatusName() != null) {
            statusToUpdate.setStatusName(status.getStatusName());
        }
        return createStatus(statusToUpdate);
    }

    @Override
    public Status deleteStatusById(Long statusId) {
        Status statusToDelete = getStatusById(statusId);
        statusRepository.delete(statusToDelete);
        return statusToDelete;
    }
}
