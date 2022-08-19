package com.example.taskmanager.service.impl;

import com.example.taskmanager.model.Status;
import com.example.taskmanager.model.TaskList;
import com.example.taskmanager.repository.TaskListRepository;
import com.example.taskmanager.service.StatusService;
import com.example.taskmanager.service.TaskListServise;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskListServiceImpl implements TaskListServise {
    private final TaskListRepository taskListRepository;
    private final StatusService statusService;

    public TaskListServiceImpl(TaskListRepository taskListRepository,
                               StatusService statusService) {
        this.taskListRepository = taskListRepository;
        this.statusService = statusService;
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if (taskList.getStatus() == null) {
            taskList.setStatus(statusService.getStatusByName(Status.StatusName.TO_DO));
        }
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
        return taskListToUpdate;
    }

    @Override
    public void deleteTaskList(Long taskListId) {
        TaskList taskListToDelete = getTaskListById(taskListId);
        taskListRepository.delete(taskListToDelete);
//        return taskListToDelete;
    }
}
