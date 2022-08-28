package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.request.UserRequestDto;
import com.example.taskmanager.dto.response.UserResponseDto;
import com.example.taskmanager.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements RequestMapper<UserRequestDto, User>,
        ResponseMapper<UserResponseDto, User> {
    @Override
    public User mapToModel(UserRequestDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    @Override
    public UserResponseDto mapToDto(User model) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(model.getId());
        userResponseDto.setName(model.getName());
        userResponseDto.setEmail(model.getEmail());
        return userResponseDto;
    }
}
