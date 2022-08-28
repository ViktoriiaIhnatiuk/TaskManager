package com.example.taskmanager.controller;

import com.example.taskmanager.dto.request.TaskListRequestDto;
import com.example.taskmanager.dto.response.TaskListResponseDto;
import com.example.taskmanager.mapper.RequestMapper;
import com.example.taskmanager.mapper.ResponseMapper;
import com.example.taskmanager.model.TaskList;
import com.example.taskmanager.service.TaskListServise;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasklists")
public class TaskListController {
    private final TaskListServise taskListServise;
    private final RequestMapper<TaskListRequestDto, TaskList> taskListRequestMapper;
    private final ResponseMapper<TaskListResponseDto, TaskList> taskListResponseMapper;

    public TaskListController(TaskListServise taskListServise,
                          RequestMapper<TaskListRequestDto, TaskList> taskListRequestMapper,
                          ResponseMapper<TaskListResponseDto, TaskList> taskListResponseMapper) {
        this.taskListServise = taskListServise;
        this.taskListRequestMapper = taskListRequestMapper;
        this.taskListResponseMapper = taskListResponseMapper;
    }

    @PostMapping
    public TaskListResponseDto createTaskList(@RequestBody TaskListRequestDto taskListRequestDto) {
        return taskListResponseMapper.mapToDto(
                taskListServise.createTaskList(taskListRequestMapper
                        .mapToModel(taskListRequestDto)));
    }

    @GetMapping("/{id}")
    public TaskListResponseDto getTaskListById(@PathVariable Long id) {
        return taskListResponseMapper.mapToDto(taskListServise.getTaskListById(id));
    }

    @GetMapping
    public List<TaskListResponseDto> getAllTaskLists() {
        return taskListServise.getAllTaskLists().stream()
                .map(taskListResponseMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public TaskListResponseDto updateTaskListById(@PathVariable Long id,
                                             @RequestBody TaskListRequestDto taskListRequestDto) {
        return taskListResponseMapper.mapToDto(taskListServise.updateTaskListById(id,
                taskListRequestMapper.mapToModel(taskListRequestDto)));
    }

    @DeleteMapping("/{id}")
    public void deleteTaskListById(@PathVariable Long id) {
        taskListServise.deleteTaskList(id);
    }
}
