package com.thoughtclan.taskhub.service;

import com.thoughtclan.taskhub.dto.CreateProjectDto;
import com.thoughtclan.taskhub.dto.ProjectDto;

public interface ProjectService {
    ProjectDto createProject(CreateProjectDto dto);
    ProjectDto getProject(Long id);
}
