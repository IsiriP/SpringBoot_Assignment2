package com.thoughtclan.taskhub.service;

import com.thoughtclan.taskhub.exception.BusinessException;
import com.thoughtclan.taskhub.model.*;
import com.thoughtclan.taskhub.model.enums.Priority;
import com.thoughtclan.taskhub.model.enums.TaskStatus;
import com.thoughtclan.taskhub.repository.*;
import com.thoughtclan.taskhub.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock private TaskRepository taskRepository;
    @Mock private DeveloperRepository developerRepository;
    @Mock private ProjectRepository projectRepository;

    @InjectMocks private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void whenCreate6thInProgressTask_thenBusinessException() {
        Developer dev = Developer.builder().id(1L).name("Dev").email("d@example.com").build();
        Project project = Project.builder().id(1L).name("P").build();

        when(developerRepository.findById(1L)).thenReturn(Optional.of(dev));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskRepository.countByDeveloperIdAndStatus(1L, TaskStatus.IN_PROGRESS)).thenReturn(5L);

        com.thoughtclan.taskhub.dto.CreateTaskDto dto = new com.thoughtclan.taskhub.dto.CreateTaskDto("T", "d", TaskStatus.IN_PROGRESS, Priority.MEDIUM, LocalDate.now().plusDays(5), 1L, 1L);

        BusinessException ex = assertThrows(BusinessException.class, () -> taskService.createTask(dto));
        assertTrue(ex.getMessage().contains("already has 5 tasks"));
    }

    @Test
    void whenUpdateDoneTask_thenBusinessException() {
        Task t = Task.builder().id(10L).title("T").status(TaskStatus.DONE).build();
        when(taskRepository.findById(10L)).thenReturn(Optional.of(t));
        BusinessException ex = assertThrows(BusinessException.class, () -> taskService.updateTaskStatus(10L, TaskStatus.TODO));
        assertTrue(ex.getMessage().contains("already DONE"));
    }
}
