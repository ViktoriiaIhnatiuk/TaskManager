package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.request.StatusRequestDto;
import com.example.taskmanager.dto.response.StatusResponseDto;
import com.example.taskmanager.model.Status;
import com.example.taskmanager.service.StatusService;
import org.springframework.stereotype.Component;

@Component
public class StatusMapper implements RequestMapper<StatusRequestDto, Status>,
        ResponseMapper<StatusResponseDto, Status> {
    private final StatusService statusService;

    public StatusMapper(StatusService statusService) {
        this.statusService = statusService;
    }

    @Override
    public StatusResponseDto mapToDto(Status model) {
        StatusResponseDto statusResponseDto = new StatusResponseDto();
        statusResponseDto.setId(model.getId());
        statusResponseDto.setStatusName(model.getStatusName().name());
        return statusResponseDto;
    }

    @Override
    public Status mapToModel(StatusRequestDto dto) {
        Status status = new Status();
        status.setStatusName(statusService.getStatusByName(dto.getStatusName()).getStatusName());
        return status;
    }
}
