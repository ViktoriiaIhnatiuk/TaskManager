package com.example.taskmanager.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class TaskListResponseDto {
    private Long id;
    private String name;
    private StatusResponseDto status;
    private List<TaskResponseDto> tasks = new ArrayList<>();
    private UserResponseDto user;
    private LocalDateTime deadline;
    private PriorityResponseDto priority;
    private boolean isTerminated;
    private String percentageOfCompletion = "0";
}
