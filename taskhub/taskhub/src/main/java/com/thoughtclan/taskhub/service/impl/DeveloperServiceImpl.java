package com.thoughtclan.taskhub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.thoughtclan.taskhub.service.DeveloperService;
import com.thoughtclan.taskhub.dto.CreateDeveloperDto;
import com.thoughtclan.taskhub.dto.DeveloperDto;
import com.thoughtclan.taskhub.repository.DeveloperRepository;
import com.thoughtclan.taskhub.repository.ProjectRepository;
import com.thoughtclan.taskhub.model.Developer;
import com.thoughtclan.taskhub.model.Project;

@Service
@RequiredArgsConstructor
@Transactional
public class DeveloperServiceImpl implements DeveloperService {

    private final DeveloperRepository developerRepository;
    private final ProjectRepository projectRepository;

    @Override
    public DeveloperDto createDeveloper(CreateDeveloperDto dto) {
        Developer d = Developer.builder().name(dto.getName()).email(dto.getEmail()).build();
        Developer saved = developerRepository.save(d);
        return new DeveloperDto(saved.getId(), saved.getName(), saved.getEmail());
    }

    @Override
    public DeveloperDto getDeveloper(Long id) {
        Developer d = developerRepository.findById(id).orElseThrow(() -> new com.thoughtclan.taskhub.exception.ResourceNotFoundException("Developer not found"));
        return new DeveloperDto(d.getId(), d.getName(), d.getEmail());
    }

    @Override
    public DeveloperDto assignDeveloperToProject(Long developerId, Long projectId) {
        Developer dev = developerRepository.findById(developerId).orElseThrow(() -> new com.thoughtclan.taskhub.exception.ResourceNotFoundException("Developer not found"));
        Project pr = projectRepository.findById(projectId).orElseThrow(() -> new com.thoughtclan.taskhub.exception.ResourceNotFoundException("Project not found"));
        dev.getProjects().add(pr);
        pr.getDevelopers().add(dev);
        developerRepository.save(dev);
        projectRepository.save(pr);
        return new DeveloperDto(dev.getId(), dev.getName(), dev.getEmail());
    }
}
