package com.example.taskmanager.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskListRequestDto {
    private String name;
    private Long statusId;
    private LocalDateTime deadline;
}
