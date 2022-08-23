package com.example.taskmanager.controller;

import com.example.taskmanager.dto.request.UserRequestDto;
import com.example.taskmanager.dto.response.UserResponseDto;
import com.example.taskmanager.mapper.ResponseMapper;
import com.example.taskmanager.model.User;
import com.example.taskmanager.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthenticationController {
    private final AuthenticationService authService;
    private final ResponseMapper<UserResponseDto, User> userDtoResponseMapper;

    public AuthenticationController(AuthenticationService authService,
            ResponseMapper<UserResponseDto, User> userDtoResponseMapper) {
        this.authService = authService;
        this.userDtoResponseMapper = userDtoResponseMapper;
    }

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRequestDto requestDto) {
        User user = authService.register(requestDto.getEmail(), requestDto.getPassword());
        return userDtoResponseMapper.mapToDto(user);
    }
}
