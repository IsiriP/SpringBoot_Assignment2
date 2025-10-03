package com.thoughtclan.taskhub.service;

import com.thoughtclan.taskhub.dto.CreateDeveloperDto;
import com.thoughtclan.taskhub.dto.DeveloperDto;

public interface DeveloperService {
    DeveloperDto createDeveloper(CreateDeveloperDto dto);
    DeveloperDto getDeveloper(Long id);
    DeveloperDto assignDeveloperToProject(Long developerId, Long projectId);
}
