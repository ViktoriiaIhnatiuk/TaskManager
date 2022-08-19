package com.example.taskmanager.config;

import com.example.taskmanager.model.Status;
import com.example.taskmanager.service.StatusService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Datainitializer {
    private final StatusService statusService;

    public Datainitializer(StatusService statusService) {
        this.statusService = statusService;
    }

    @PostConstruct
    public void inject() {
        Status toDoStatus = new Status(Status.StatusName.TO_DO);
        statusService.createStatus(toDoStatus);
        Status inProgressStatus = new Status(Status.StatusName.IN_PROGRESS);
        statusService.createStatus(inProgressStatus);
        Status doneStatus = new Status(Status.StatusName.DONE);
        statusService.createStatus(doneStatus);
    }
}
