package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class UserFilterDto extends FilterDto{
    private String firstName;
    private String lastName;
    private Integer userTypeId;
    private String username;
    private String phone;
    private Boolean status;
    private LocalDateTime minCreatedDate;
    private LocalDateTime maxCreatedDate;
}
