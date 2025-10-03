package com.thoughtclan.taskhub.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.thoughtclan.taskhub.service.ProjectService;
import com.thoughtclan.taskhub.repository.ProjectRepository;
import com.thoughtclan.taskhub.dto.CreateProjectDto;
import com.thoughtclan.taskhub.dto.ProjectDto;
import com.thoughtclan.taskhub.model.Project;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public ProjectDto createProject(CreateProjectDto dto) {
        Project p = Project.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
        Project saved = projectRepository.save(p);
        return new ProjectDto(saved.getId(), saved.getName(), saved.getDescription());
    }

    @Override
    public ProjectDto getProject(Long id) {
        Project p = projectRepository.findById(id).orElseThrow(() -> new com.thoughtclan.taskhub.exception.ResourceNotFoundException("Project not found"));
        return new ProjectDto(p.getId(), p.getName(), p.getDescription());
    }
}
