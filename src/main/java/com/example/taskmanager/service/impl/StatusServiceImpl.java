package com.example.taskmanager.service.impl;

import com.example.taskmanager.model.Status;
import com.example.taskmanager.repository.StatusRepository;
import com.example.taskmanager.service.StatusService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Status getStatusByName(Status.StatusName statusName) {
        return statusRepository.findByStatusName(statusName).orElseThrow(
                () -> new RuntimeException("Can't find status by statusName " + statusName));
    }

    @Override
    public Status getStatusById(Long statusId) {
        return statusRepository.findById(statusId).orElseThrow(
                () -> new RuntimeException("Can't find status bu id " + statusId));
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
