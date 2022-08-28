package com.example.taskmanager.controller;

import com.example.taskmanager.dto.request.TaskRequestDto;
import com.example.taskmanager.dto.response.TaskResponseDto;
import com.example.taskmanager.mapper.RequestMapper;
import com.example.taskmanager.mapper.ResponseMapper;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
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
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final RequestMapper<TaskRequestDto, Task> taskRequestMapper;
    private final ResponseMapper<TaskResponseDto, Task> taskResponseMapper;

    public TaskController(TaskService taskService,
                          RequestMapper<TaskRequestDto, Task> taskRequestMapper,
                          ResponseMapper<TaskResponseDto, Task> taskResponseMapper) {
        this.taskService = taskService;
        this.taskRequestMapper = taskRequestMapper;
        this.taskResponseMapper = taskResponseMapper;
    }

    @PostMapping("/tasklists/{id}")
    public TaskResponseDto createTask(@PathVariable Long id,
                                      @RequestBody TaskRequestDto taskRequestDto) {
        return taskResponseMapper.mapToDto(taskService.creteTask(id,
                taskRequestMapper.mapToModel(taskRequestDto)));
    }

    @GetMapping("/{id}")
    public TaskResponseDto getTaskById(@PathVariable Long id) {
        return taskResponseMapper.mapToDto(taskService.getTaskById(id));
    }

    @GetMapping
    public List<TaskResponseDto> getAllTasks() {
        return taskService.getAllTasks().stream()
                .map(taskResponseMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public TaskResponseDto updateTaskById(@PathVariable Long id,
                                          @RequestBody TaskRequestDto taskRequestDto) {
        return taskResponseMapper.mapToDto(taskService.updateTaskById(id,
                taskRequestMapper.mapToModel(taskRequestDto)));
    }

    @DeleteMapping("/{id}")
    public TaskResponseDto deleteTaskById(@PathVariable Long id) {
        return taskResponseMapper.mapToDto(taskService.deleteTaskById(id));
    }
}
