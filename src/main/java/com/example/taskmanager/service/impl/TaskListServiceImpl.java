package com.example.taskmanager.service.impl;

import com.example.taskmanager.model.TaskList;
import com.example.taskmanager.repository.TaskListRepository;
import com.example.taskmanager.service.TaskListServise;

import java.util.List;

public class TaskListServiceImpl implements TaskListServise {
    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        return taskListRepository.save(taskList);
    }

    @Override
    public TaskList getTaskListById(Long taskListId) {
        return taskListRepository.findById(taskListId).orElseThrow(
                () -> new RuntimeException("Can't find taskList by id " + taskListId));
    }

    @Override
    public List<TaskList> getAllTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList updateTaskListById(Long taskListId, TaskList taskList) {
        TaskList taskListToUpdate = getTaskListById(taskListId);
        if (taskList.getName() != null) {
            taskListToUpdate.setName(taskList.getName());
        }
        if (taskList.getDeadline() != null) {
            taskListToUpdate.setDeadline(taskList.getDeadline());
        }
        if (taskList.getTasks() != null) {
            taskListToUpdate.setTasks(taskList.getTasks());
        }
        return null;
    }

    @Override
    public TaskList deleteTaskList(Long taskList) {
        return null;
    }
}
