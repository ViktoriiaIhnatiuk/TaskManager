package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.request.PriorityRequestDto;
import com.example.taskmanager.dto.response.PriorityResponseDto;
import com.example.taskmanager.model.Priority;
import com.example.taskmanager.service.PriorityService;
import org.springframework.stereotype.Component;

@Component
public class PriorityMapper implements RequestMapper<PriorityRequestDto, Priority>,
        ResponseMapper<PriorityResponseDto, Priority> {
    private final PriorityService priorityService;

    public PriorityMapper(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @Override
    public PriorityResponseDto mapToDto(Priority model) {
        PriorityResponseDto priorityResponseDto = new PriorityResponseDto();
        priorityResponseDto.setId(model.getId());
        priorityResponseDto.setPriority(model.getPriorityName().name());
        return priorityResponseDto;
    }

    @Override
    public Priority mapToModel(PriorityRequestDto dto) {
        Priority priority = new Priority();
        priority.setPriorityName(priorityService.getPriorityByName(
                dto.getPriorityName()).getPriorityName());
        return priority;
    }
}
