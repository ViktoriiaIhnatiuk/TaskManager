package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.request.TaskListRequestDto;
import com.example.taskmanager.dto.response.TaskListResponseDto;
import com.example.taskmanager.model.TaskList;
import com.example.taskmanager.service.PriorityService;
import com.example.taskmanager.service.StatusService;
import com.example.taskmanager.service.UserService;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TaskListMapper implements RequestMapper<TaskListRequestDto, TaskList>,
        ResponseMapper<TaskListResponseDto, TaskList> {
    private final UserMapper userMapper;
    private final StatusMapper statusMapper;
    private final TaskMapper taskMapper;
    private final StatusService statusService;
    private final PriorityService priorityService;
    private final PriorityMapper priorityMapper;
    private final UserService userService;

    public TaskListMapper(UserMapper userMapper,
                          StatusMapper statusMapper,
                          TaskMapper taskMapper,
                          StatusService statusService,
                          PriorityService priorityService,
                          PriorityMapper priorityMapper,
                          UserService userService) {
        this.userMapper = userMapper;
        this.statusMapper = statusMapper;
        this.taskMapper = taskMapper;
        this.statusService = statusService;
        this.priorityService = priorityService;
        this.priorityMapper = priorityMapper;
        this.userService = userService;
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
        if (dto.getPriorityId() != null) {
            taskList.setPriority(priorityService.getPriorityById(dto.getPriorityId()));
        }
        if (dto.getUserId() != null) {
            taskList.setUser(userService.getUserById(dto.getUserId()));
        }
        return taskList;
    }

    @Override
    public TaskListResponseDto mapToDto(TaskList model) {
        TaskListResponseDto taskListResponseDto = new TaskListResponseDto();
        taskListResponseDto.setId(model.getId());
        taskListResponseDto.setName(model.getName());
        taskListResponseDto.setStatus(statusMapper.mapToDto(model.getStatus()));
        if (model.getTasks() != null) {
            taskListResponseDto.setTasks(model.getTasks().stream()
                    .map(taskMapper::mapToDto)
                    .collect(Collectors.toList()));
        }
        if (model.getUser() != null) {
            taskListResponseDto.setUser(userMapper.mapToDto(model.getUser()));
        }
        if (model.getDeadline() != null) {
            taskListResponseDto.setDeadline(model.getDeadline());
        }
        if (model.getTasks() != null) {
            taskListResponseDto.setPercentageOfCompletion(String.valueOf(
                    Math.round((model.getCounter()
                            / (double) model.getTasks().size() * 100)) + " %"));
        }
        taskListResponseDto.setTerminated(model.getIsTerminated());
        taskListResponseDto.setPriority(priorityMapper.mapToDto(model.getPriority()));
        return taskListResponseDto;
    }
}
