package com.example.taskmanager.controller;

import com.example.taskmanager.dto.request.StatusRequestDto;
import com.example.taskmanager.dto.response.StatusResponseDto;
import com.example.taskmanager.mapper.RequestMapper;
import com.example.taskmanager.mapper.ResponseMapper;
import com.example.taskmanager.model.Status;
import com.example.taskmanager.service.StatusService;
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
@RequestMapping("/statuses")
public class StatusController {
    private final StatusService statusService;
    private final RequestMapper<StatusRequestDto, Status> statusRequestMapper;
    private final ResponseMapper<StatusResponseDto, Status> statusResponseMapper;

    public StatusController(StatusService statusService,
                            RequestMapper<StatusRequestDto, Status> statusRequestMapper,
                            ResponseMapper<StatusResponseDto, Status> statusResponseMapper) {
        this.statusService = statusService;
        this.statusRequestMapper = statusRequestMapper;
        this.statusResponseMapper = statusResponseMapper;
    }

    @PostMapping
    public StatusResponseDto createStatus(@RequestBody StatusRequestDto statusRequestDto) {
        return statusResponseMapper.mapToDto(
                statusService.createStatus(statusRequestMapper.mapToModel(statusRequestDto)));
    }

    @PutMapping("/{id}")
    public StatusResponseDto updateStatusById(@PathVariable Long id,
                                              @RequestBody StatusRequestDto statusRequestDto) {
        return statusResponseMapper.mapToDto(
                statusService.updateStatusById(id,
                        statusRequestMapper.mapToModel(statusRequestDto)));
    }

    @GetMapping
    public List<StatusResponseDto> getAllStatuses() {
        return statusService.getAllStatuses().stream()
                .map(statusResponseMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public StatusResponseDto getStatusById(@PathVariable Long id) {
        return statusResponseMapper.mapToDto(statusService.getStatusById(id));
    }

    @DeleteMapping("/{id}")
    public StatusResponseDto deleteStatusById(@PathVariable Long id) {
        return statusResponseMapper.mapToDto(statusService.deleteStatusById(id));
    }
}
