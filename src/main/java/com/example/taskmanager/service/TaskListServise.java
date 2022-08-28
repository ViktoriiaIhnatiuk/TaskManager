package com.example.taskmanager.service;

import com.example.taskmanager.model.TaskList;
import java.util.List;

public interface TaskListServise {
    TaskList createTaskList(TaskList taskList);

    TaskList getTaskListById(Long taskListId);

    List<TaskList> getAllTaskLists();

    TaskList updateTaskListById(Long taskListId, TaskList taskList);

    TaskList deleteTaskList(Long taskList);
}
