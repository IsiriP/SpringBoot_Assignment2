package com.thoughtclan.taskhub.service;

import com.thoughtclan.taskhub.dto.CreateTaskDto;
import com.thoughtclan.taskhub.dto.TaskDto;
import com.thoughtclan.taskhub.model.enums.TaskStatus;
import java.util.List;

public interface TaskService {
    TaskDto createTask(CreateTaskDto dto);
    List<TaskDto> getTasksForDeveloper(Long developerId);
    List<TaskDto> getTasksForProject(Long projectId);
    TaskDto updateTaskStatus(Long taskId, TaskStatus newStatus);
    List<TaskDto> getOverdueTasks();
    List<com.thoughtclan.taskhub.dto.DeveloperDto> topDevelopersByOverdueTasks(int topN);
}
