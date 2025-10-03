package com.thoughtclan.taskhub.controller;

import com.thoughtclan.taskhub.dto.*;
import com.thoughtclan.taskhub.service.DeveloperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/developers")
@RequiredArgsConstructor
public class DeveloperController {

    private final DeveloperService developerService;

    @PostMapping
    public DeveloperDto createDeveloper(@Valid @RequestBody CreateDeveloperDto dto) {
        return developerService.createDeveloper(dto);
    }

    @PostMapping("/{devId}/projects/{projectId}")
    public DeveloperDto assignToProject(@PathVariable Long devId, @PathVariable Long projectId) {
        return developerService.assignDeveloperToProject(devId, projectId);
    }

    @GetMapping("/{id}")
    public DeveloperDto getDeveloper(@PathVariable Long id) {
        return developerService.getDeveloper(id);
    }
}
