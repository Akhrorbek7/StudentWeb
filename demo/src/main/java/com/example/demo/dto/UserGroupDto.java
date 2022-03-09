package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class UserGroupDto {
    private Long id;
    private UserDto user;
    @NotNull(message = "User id invalid")
    private Long userId;
    private GroupDto group;
    @NotNull(message = "Group id invalid")
    private Long groupId;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
