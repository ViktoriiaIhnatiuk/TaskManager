package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.request.TaskListRequestDto;
import com.example.taskmanager.dto.response.TaskListResponseDto;
import com.example.taskmanager.model.TaskList;
import com.example.taskmanager.service.StatusService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TaskListMapper implements RequestMapper<TaskListRequestDto, TaskList>,
ResponseMapper<TaskListResponseDto, TaskList> {
    private final UserMapper userMapper;
    private final StatusMapper statusMapper;
    private final TaskMapper taskMapper;
    private final StatusService statusService;

    public TaskListMapper(UserMapper userMapper,
                          StatusMapper statusMapper,
                          TaskMapper taskMapper,
                          StatusService statusService) {
        this.userMapper = userMapper;
        this.statusMapper = statusMapper;
        this.taskMapper = taskMapper;
        this.statusService = statusService;
    }

    @Override
    public TaskList mapToModel(TaskListRequestDto dto) {
        TaskList taskList = new TaskList();
        taskList.setName(dto.getName());
        if (dto.getDeadline() != null) {
            taskList.setDeadline(dto.getDeadline());
        }
        if (dto.getStatusId() != null) {
            taskList.setStatus(statusService.getStatusById(dto.getStatusId()));
        }
        return taskList;
    }

    @Override
    public TaskListResponseDto mapToDto(TaskList model) {
        TaskListResponseDto taskListResponseDto = new TaskListResponseDto();
        taskListResponseDto.setId(model.getId());
        taskListResponseDto.setName(model.getName());
        if (model.getStatus() != null) {
            taskListResponseDto.setStatus(statusMapper.mapToDto(model.getStatus()));
        }
            if (model.getTasks() != null) {
            taskListResponseDto.setTasks(model.getTasks().stream()
                    .map(taskMapper::mapToDto)
                    .collect(Collectors.toList()));
        }
        taskListResponseDto.setDeadline(model.getDeadline());
        if (model.getUser() != null) {
            taskListResponseDto.setUser(userMapper.mapToDto(model.getUser()));
        }
        if (model.getDeadline() != null) {
            taskListResponseDto.setDeadline(model.getDeadline());
        }
        return taskListResponseDto;
    }
}
