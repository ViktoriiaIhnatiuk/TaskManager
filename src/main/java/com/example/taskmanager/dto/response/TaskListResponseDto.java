package com.example.taskmanager.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class TaskListResponseDto {
    private Long id;
    private String name;
    private StatusResponseDto status;
    private List<TaskResponseDto> tasks;
    private LocalDateTime deadline;
    private UserResponseDto user;
}
