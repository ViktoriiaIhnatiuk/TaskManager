package com.example.taskmanager.mapper;

public interface RequestMapper<D, M> {
    M mapToModel(D dto);
}
