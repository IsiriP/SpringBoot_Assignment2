package com.thoughtclan.taskhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeveloperDto {
    @NotBlank
    private String name;

    @NotBlank @Email
    private String email;
}
