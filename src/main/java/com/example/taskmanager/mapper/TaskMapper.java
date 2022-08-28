package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.request.TaskRequestDto;
import com.example.taskmanager.dto.response.TaskResponseDto;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.PriorityService;
import com.example.taskmanager.service.StatusService;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper implements RequestMapper<TaskRequestDto, Task>,
        ResponseMapper<TaskResponseDto, Task> {
    private final StatusMapper statusMapper;
    private final StatusService statusService;
    private final PriorityMapper priorityMapper;
    private final PriorityService priorityService;

    public TaskMapper(StatusMapper statusMapper,
                      StatusService statusService,
                      PriorityMapper priorityMapper,
                      PriorityService priorityService) {
        this.statusMapper = statusMapper;
        this.statusService = statusService;
        this.priorityMapper = priorityMapper;
        this.priorityService = priorityService;
    }

    @Override
    public Task mapToModel(TaskRequestDto dto) {
        Task task = new Task();
        task.setName(dto.getName());
        if (dto.getStatusId() != null) {
            task.setStatus(statusService.getStatusById(dto.getStatusId()));
        }
        if (dto.getPriorityId() != null) {
            task.setPriority(priorityService.getPriorityById(dto.getPriorityId()));
        }
        return task;
    }

    @Override
    public TaskResponseDto mapToDto(Task model) {
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setName(model.getName());
        taskResponseDto.setId(model.getId());
        taskResponseDto.setDate(model.getDate());
        taskResponseDto.setTerminated(model.getIsTerminated());
        taskResponseDto.setStatus(statusMapper.mapToDto(model.getStatus()));
        taskResponseDto.setPriority(priorityMapper.mapToDto(model.getPriority()));
        return taskResponseDto;
    }
}
