package com.example.taskmanager.service;

import com.example.taskmanager.model.Priority;
import java.util.List;

public interface PriorityService {
    Priority createPriority(Priority priority);

    Priority getPriorityByName(String priorityName);

    Priority getPriorityById(Long priorityId);

    List<Priority> getAllPriorities();

    Priority updatePriorityById(Long priorityId, Priority priority);

    Priority deletePriorityById(Long priorityId);
}
