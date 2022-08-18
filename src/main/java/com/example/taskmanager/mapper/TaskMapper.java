package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.request.TaskRequestDto;
import com.example.taskmanager.dto.response.TaskResponseDto;
import com.example.taskmanager.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper implements RequestMapper<TaskRequestDto, Task>,
        ResponseMapper<TaskResponseDto, Task> {
    private final StatusMapper statusMapper;

    public TaskMapper(StatusMapper statusMapper) {
        this.statusMapper = statusMapper;
    }

    @Override
    public Task mapToModel(TaskRequestDto dto) {
        Task task = new Task();
        task.setName(dto.getName());
//        task.setStatus();

        return task;
    }

    @Override
    public TaskResponseDto mapToDto(Task model) {
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setId(model.getId());
        taskResponseDto.setDate(model.getDate());
        taskResponseDto.setTerminated(model.isTerminated());
        taskResponseDto.setStatus(statusMapper.mapToDto(model.getStatus()));
        return taskResponseDto;
    }
}
