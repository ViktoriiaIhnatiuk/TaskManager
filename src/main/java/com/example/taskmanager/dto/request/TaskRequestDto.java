package com.example.taskmanager.dto.request;

import lombok.Data;

@Data
public class TaskRequestDto {
    private String name;
    private Long priorityId;
    private Long statusId;
}
