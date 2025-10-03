package com.thoughtclan.taskhub.controller;

import com.thoughtclan.taskhub.dto.*;
import com.thoughtclan.taskhub.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ProjectDto createProject(@Valid @RequestBody CreateProjectDto dto) {
        return projectService.createProject(dto);
    }

    @GetMapping("/{id}")
    public ProjectDto getProject(@PathVariable Long id) {
        return projectService.getProject(id);
    }
}
