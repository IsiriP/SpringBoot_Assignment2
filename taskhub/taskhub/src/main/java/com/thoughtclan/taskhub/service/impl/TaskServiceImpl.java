package com.thoughtclan.taskhub.service.impl;

import com.thoughtclan.taskhub.dto.CreateTaskDto;
import com.thoughtclan.taskhub.dto.DeveloperDto;
import com.thoughtclan.taskhub.dto.TaskDto;
import com.thoughtclan.taskhub.exception.BusinessException;
import com.thoughtclan.taskhub.exception.ResourceNotFoundException;
import com.thoughtclan.taskhub.model.Developer;
import com.thoughtclan.taskhub.model.Project;
import com.thoughtclan.taskhub.model.Task;
import com.thoughtclan.taskhub.model.enums.TaskStatus;
import com.thoughtclan.taskhub.repository.DeveloperRepository;
import com.thoughtclan.taskhub.repository.ProjectRepository;
import com.thoughtclan.taskhub.repository.TaskRepository;
import com.thoughtclan.taskhub.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final DeveloperRepository developerRepository;
    private final ProjectRepository projectRepository;

    private TaskDto toDto(Task t) {
        return new TaskDto(
                t.getId(),
                t.getTitle(),
                t.getDescription(),
                t.getStatus(),
                t.getPriority(),
                t.getDueDate(),
                t.getCreatedAt(),
                t.getDeveloper() == null ? null : t.getDeveloper().getId(),
                t.getProject() == null ? null : t.getProject().getId()
        );
    }

    @Override
    public TaskDto createTask(CreateTaskDto dto) {
        Developer dev = developerRepository.findById(dto.getDeveloperId())
                .orElseThrow(() -> new ResourceNotFoundException("Developer not found"));

        Project pr = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        // Business rule: developer cannot have >5 IN_PROGRESS tasks
        if (dto.getStatus() == TaskStatus.IN_PROGRESS) {
            long inProgressCount = taskRepository.countByDeveloperIdAndStatus(dev.getId(), TaskStatus.IN_PROGRESS);
            if (inProgressCount >= 5) {
                throw new BusinessException("Developer already has 5 tasks IN_PROGRESS");
            }
        }

        Task t = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .priority(dto.getPriority())
                .dueDate(dto.getDueDate())
                .developer(dev)
                .project(pr)
                .build();

        Task saved = taskRepository.save(t);
        return toDto(saved);
    }

    @Override
    public List<TaskDto> getTasksForDeveloper(Long developerId) {
        // validate existence first
        developerRepository.findById(developerId)
                .orElseThrow(() -> new ResourceNotFoundException("Developer not found"));
        return taskRepository.findByDeveloperId(developerId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getTasksForProject(Long projectId) {
        projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return taskRepository.findByProjectId(projectId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto updateTaskStatus(Long taskId, TaskStatus newStatus) {
        Task t = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        // Prevent updates to tasks marked as DONE
        if (t.getStatus() == TaskStatus.DONE) {
            throw new BusinessException("Cannot update a task that is already DONE");
        }

        // If changing to IN_PROGRESS, enforce 5-task limit for developer
        if (newStatus == TaskStatus.IN_PROGRESS) {
            Long devId = t.getDeveloper() == null ? null : t.getDeveloper().getId();
            if (devId != null) {
                long inProgressCount = taskRepository.countByDeveloperIdAndStatus(devId, TaskStatus.IN_PROGRESS);
                // if the current task is already IN_PROGRESS it would have been counted; here we are changing into IN_PROGRESS
                if (inProgressCount >= 5) {
                    throw new BusinessException("Developer already has 5 tasks IN_PROGRESS");
                }
            }
        }

        t.setStatus(newStatus);
        Task saved = taskRepository.save(t);
        return toDto(saved);
    }

    @Override
    public List<TaskDto> getOverdueTasks() {
        LocalDate today = LocalDate.now();
        // pass TaskStatus.DONE so repository can exclude completed tasks
        return taskRepository.findOverdueTasks(today, TaskStatus.DONE).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeveloperDto> topDevelopersByOverdueTasks(int topN) {
        LocalDate today = LocalDate.now();

        // IMPORTANT: repository method signature expects (LocalDate date, TaskStatus doneStatus, Pageable)
        // pass TaskStatus.DONE to exclude DONE tasks when counting overdue
        List<Object[]> rows = taskRepository.findDeveloperIdAndOverdueCount(today, TaskStatus.DONE, PageRequest.of(0, topN));

        if (rows == null || rows.isEmpty()) {
            return Collections.emptyList();
        }

        // rows are Object[] where [0] = developerId, [1] = overdueCount
        List<Long> devIds = rows.stream()
                .map(r -> ((Number) r[0]).longValue())
                .collect(Collectors.toList());

        List<Developer> devs = developerRepository.findAllById(devIds);

        // map by id for O(1) lookup
        Map<Long, Developer> map = devs.stream()
                .collect(Collectors.toMap(Developer::getId, d -> d));

        // preserve order from devIds and convert to DeveloperDto
        List<DeveloperDto> result = new ArrayList<>();
        for (Object[] r : rows) {
            Long developerId = ((Number) r[0]).longValue();
            Developer d = map.get(developerId);
            if (d != null) {
                // DeveloperDto constructor assumed (id, name, email) — keep same shape as you used earlier
                result.add(new DeveloperDto(d.getId(), d.getName(), d.getEmail()));
            } else {
                // developer missing in DB? skip or handle accordingly
            }
        }
        return result;
    }
}
