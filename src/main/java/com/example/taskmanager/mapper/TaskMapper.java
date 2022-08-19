package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.request.TaskRequestDto;
import com.example.taskmanager.dto.response.TaskResponseDto;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.StatusService;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper implements RequestMapper<TaskRequestDto, Task>,
        ResponseMapper<TaskResponseDto, Task> {
    private final StatusMapper statusMapper;
    private final StatusService statusService;

    public TaskMapper(StatusMapper statusMapper, StatusService statusService) {
        this.statusMapper = statusMapper;
        this.statusService = statusService;
    }

    @Override
    public Task mapToModel(TaskRequestDto dto) {
        Task task = new Task();
        task.setName(dto.getName());
        if (dto.getStatusId() != null) {
            task.setStatus(statusService.getStatusById(dto.getStatusId()));
        }
        return task;
    }

    @Override
    public TaskResponseDto mapToDto(Task model) {
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setName(model.getName());
        taskResponseDto.setId(model.getId());
        taskResponseDto.setDate(model.getDate());
        taskResponseDto.setTerminated(model.isTerminated());
        if (model.getStatus() != null) {
            taskResponseDto.setStatus(statusMapper.mapToDto(model.getStatus()));
        }
        return taskResponseDto;
    }
}
