package com.example.taskmanager.controller;

import com.example.taskmanager.dto.request.UserRequestDto;
import com.example.taskmanager.dto.response.UserResponseDto;
import com.example.taskmanager.exception.AuthenticationException;
import com.example.taskmanager.mapper.ResponseMapper;
import com.example.taskmanager.model.User;
import com.example.taskmanager.security.jwt.JwtTokenProvider;
import com.example.taskmanager.service.AuthenticationService;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authService;
    private final ResponseMapper<UserResponseDto, User> userDtoResponseMapper;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationController(AuthenticationService authService,
                                    ResponseMapper<UserResponseDto, User> userDtoResponseMapper,
                                    JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.userDtoResponseMapper = userDtoResponseMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRequestDto requestDto) {
        User user = authService.register(requestDto.getName(), requestDto.getEmail(),
                requestDto.getPassword());
        return userDtoResponseMapper.mapToDto(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserRequestDto requestDto)
            throws AuthenticationException {
        User user = authService.login(requestDto.getEmail(), requestDto.getPassword());
        String token = jwtTokenProvider.createToken(user.getEmail(),
                user.getRoles().stream()
                .map(e -> e.getRoleName().name())
                .collect(Collectors.toList()));
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }
}
