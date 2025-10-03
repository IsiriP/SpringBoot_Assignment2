package com.thoughtclan.taskhub.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperDto {
    private Long id;
    private String name;
    private String email;
}
