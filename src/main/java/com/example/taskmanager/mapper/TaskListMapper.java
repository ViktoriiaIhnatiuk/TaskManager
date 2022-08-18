package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.request.TaskListRequestDto;
import com.example.taskmanager.dto.response.TaskListResponseDto;
import com.example.taskmanager.model.TaskList;
import org.springframework.stereotype.Component;

@Component
public class TaskListMapper implements RequestMapper<TaskListRequestDto, TaskList>,
ResponseMapper<TaskListResponseDto, TaskList> {
    private final UserMapper userMapper;

    public TaskListMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    //    private final
    @Override
    public TaskList mapToModel(TaskListRequestDto dto) {
        TaskList taskList = new TaskList();
        taskList.setName(dto.getName());
//        taskList.setStatus();
        return taskList;
    }

    @Override
    public TaskListResponseDto mapToDto(TaskList model) {
        TaskListResponseDto taskListResponseDto = new TaskListResponseDto();
        taskListResponseDto.setId(model.getId());
        taskListResponseDto.setName(model.getName());
        taskListResponseDto.setDeadline(model.getDeadline());
        taskListResponseDto.setUser(userMapper.mapToDto(model.getUser()));
//        taskListResponseDto.setStatus();
        return taskListResponseDto;
    }
}
