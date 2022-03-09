package com.example.demo.dto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class GroupDto {
    private Long id;
    private CourseDto course;
    @NotNull(message = "Course id invalid")
    private Long courseId;
    @NotBlank(message = "Name invalid")
    private String name;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
