package com.thoughtclan.taskhub.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectDto {
    @NotBlank
    private String name;
    private String description;
}
