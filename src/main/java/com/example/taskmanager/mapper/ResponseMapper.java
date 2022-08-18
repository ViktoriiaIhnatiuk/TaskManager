package com.example.taskmanager.mapper;

public interface ResponseMapper<D, M> {
    D mapToDto(M model);
}
