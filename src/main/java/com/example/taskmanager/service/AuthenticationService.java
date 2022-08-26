package com.example.taskmanager.service;

import com.example.taskmanager.exception.AuthenticationException;
import com.example.taskmanager.model.User;

public interface AuthenticationService {
    User register(String email, String password);

    User login(String email, String password) throws AuthenticationException;
}
