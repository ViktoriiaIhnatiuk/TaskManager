package com.example.taskmanager.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TaskResponseDto {
    private Long id;
    private String name;
    private StatusResponseDto status;
    private PriorityResponseDto priority;
    private LocalDateTime date;
    private boolean isTerminated;
}
