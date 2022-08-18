package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.response.StatusResponseDto;
import com.example.taskmanager.model.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusMapper implements ResponseMapper<StatusResponseDto, Status> {
    @Override
    public StatusResponseDto mapToDto(Status model) {
        StatusResponseDto statusResponseDto = new StatusResponseDto();
        statusResponseDto.setId(model.getId());
        statusResponseDto.setStatusName(model.getStatusName().name());
        return statusResponseDto;
    }
}
