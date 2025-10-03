package com.thoughtclan.taskhub.controller;

import com.thoughtclan.taskhub.dto.*;
import com.thoughtclan.taskhub.model.enums.TaskStatus;
import com.thoughtclan.taskhub.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskDto createTask(@Valid @RequestBody CreateTaskDto dto) {
        return taskService.createTask(dto);
    }

    @GetMapping("/developer/{developerId}")
    public List<TaskDto> getTasksForDeveloper(@PathVariable Long developerId) {
        return taskService.getTasksForDeveloper(developerId);
    }

    @GetMapping("/project/{projectId}")
    public List<TaskDto> getTasksForProject(@PathVariable Long projectId) {
        return taskService.getTasksForProject(projectId);
    }

    @PutMapping("/{taskId}/status")
    public TaskDto updateStatus(@PathVariable Long taskId, @Valid @RequestBody UpdateTaskStatusDto dto) {
        return taskService.updateTaskStatus(taskId, dto.getStatus());
    }

    @GetMapping("/overdue")
    public List<TaskDto> getOverdue() { return taskService.getOverdueTasks(); }

    @GetMapping("/top-overdue-developers")
    public List<DeveloperDto> topDevelopers(@RequestParam(defaultValue = "3") int topN) {
        return taskService.topDevelopersByOverdueTasks(topN);
    }
}
