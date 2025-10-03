package com.thoughtclan.taskhub.dto;

import com.thoughtclan.taskhub.model.enums.Priority;
import com.thoughtclan.taskhub.model.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskDto {
    @NotBlank
    private String title;
    private String description;

    @NotNull
    private TaskStatus status;

    private Priority priority;

    private LocalDate dueDate;

    @NotNull
    private Long developerId;

    @NotNull
    private Long projectId;
}
