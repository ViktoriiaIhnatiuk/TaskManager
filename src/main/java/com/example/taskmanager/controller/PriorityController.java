package com.example.taskmanager.controller;

import com.example.taskmanager.dto.request.PriorityRequestDto;
import com.example.taskmanager.dto.response.PriorityResponseDto;
import com.example.taskmanager.mapper.RequestMapper;
import com.example.taskmanager.mapper.ResponseMapper;
import com.example.taskmanager.model.Priority;
import com.example.taskmanager.service.PriorityService;
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
@RequestMapping("/priorities")
public class PriorityController {
    private final RequestMapper<PriorityRequestDto, Priority> priorityRequestMapper;
    private final ResponseMapper<PriorityResponseDto, Priority> priorityResponseMapper;
    private final PriorityService priorityService;

    public PriorityController(RequestMapper<PriorityRequestDto, Priority> priorityRequestMapper,
                              ResponseMapper<PriorityResponseDto, Priority> priorityResponseMapper,
                              PriorityService priorityService) {
        this.priorityRequestMapper = priorityRequestMapper;
        this.priorityResponseMapper = priorityResponseMapper;
        this.priorityService = priorityService;
    }

    @PostMapping
    public PriorityResponseDto createPriority(@RequestBody PriorityRequestDto priorityRequestDto) {
        return priorityResponseMapper.mapToDto(priorityService.createPriority(
                priorityRequestMapper.mapToModel(priorityRequestDto)));
    }

    @PutMapping("/{id}")
    public PriorityResponseDto updatePriorityById(@PathVariable Long id,
                                              @RequestBody PriorityRequestDto priorityRequestDto) {
        return priorityResponseMapper.mapToDto(
                priorityService.updatePriorityById(id,
                        priorityRequestMapper.mapToModel(priorityRequestDto)));
    }

    @GetMapping
    public List<PriorityResponseDto> getAllPriorities() {
        return priorityService.getAllPriorities().stream()
                .map(priorityResponseMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PriorityResponseDto getPriorityById(@PathVariable Long id) {
        return priorityResponseMapper.mapToDto(priorityService.getPriorityById(id));
    }

    @DeleteMapping("/{id}")
    public PriorityResponseDto deletePriorityById(@PathVariable Long id) {
        return priorityResponseMapper.mapToDto(priorityService.deletePriorityById(id));
    }
}
