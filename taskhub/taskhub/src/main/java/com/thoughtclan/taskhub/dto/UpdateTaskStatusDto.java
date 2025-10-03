package com.thoughtclan.taskhub.dto;

import com.thoughtclan.taskhub.model.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskStatusDto {
    @NotNull
    private TaskStatus status;
}
