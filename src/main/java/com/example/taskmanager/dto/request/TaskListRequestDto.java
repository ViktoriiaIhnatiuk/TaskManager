package com.example.taskmanager.dto.request;

import lombok.Data;

@Data
public class TaskListRequestDto {
    private String name;
    private Long statusId;
}
