package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClassTypeDto {
    private Long id;
    private String name;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
