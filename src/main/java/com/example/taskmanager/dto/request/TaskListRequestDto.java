package com.example.taskmanager.dto.request;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TaskListRequestDto {
    private String name;
    private Long userId;
    private Long statusId;
    private LocalDateTime deadline;
    private Long priorityId;
}
