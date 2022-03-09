package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class UserTypeDto {
    private Long id;
    @NotBlank(message = "Invalid name")
    private String name;
    @NotBlank(message = "Invalid displayName")
    private String displayName;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
