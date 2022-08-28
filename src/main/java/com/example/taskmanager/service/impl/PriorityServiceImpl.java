package com.example.taskmanager.service.impl;

import com.example.taskmanager.model.Priority;
import com.example.taskmanager.repository.PriorityRepository;
import com.example.taskmanager.service.PriorityService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PriorityServiceImpl implements PriorityService {
    private final PriorityRepository priorityRepository;

    public PriorityServiceImpl(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @Override
    public Priority createPriority(Priority priority) {
        return priorityRepository.save(priority);
    }

    @Override
    public Priority getPriorityByName(String priorityName) {
        return priorityRepository.findPriorityByName(priorityName).orElseThrow(
                () -> new RuntimeException("Can't find priority by priorityName " + priorityName));
    }

    @Override
    public Priority getPriorityById(Long priorityId) {
        return priorityRepository.findById(priorityId).orElseThrow(
                () -> new RuntimeException("Can't find priority by id " + priorityId));
    }

    @Override
    public List<Priority> getAllPriorities() {
        return priorityRepository.findAll();
    }

    @Override
    public Priority updatePriorityById(Long priorityId, Priority priority) {
        Priority priorityToUpdate = getPriorityById(priorityId);
        if (priority.getPriorityName() != null) {
            priorityToUpdate.setPriorityName(priority.getPriorityName());
        }
        return createPriority(priorityToUpdate);
    }

    @Override
    public Priority deletePriorityById(Long priorityId) {
        Priority priorityToDelete = getPriorityById(priorityId);
        priorityRepository.delete(priorityToDelete);
        return priorityToDelete;
    }
}
