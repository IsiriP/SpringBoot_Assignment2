package com.thoughtclan.taskhub.dto;

import com.thoughtclan.taskhub.model.enums.Priority;
import com.thoughtclan.taskhub.model.enums.TaskStatus;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private Long developerId;
    private Long projectId;
}
