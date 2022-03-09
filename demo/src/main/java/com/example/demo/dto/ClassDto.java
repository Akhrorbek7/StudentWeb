package com.example.demo.dto;
import lombok.Data;

@Data
public class ClassDto {
    private Long id;
    private Long roomId;
    private RoomDto room;
    private Long classTypeId;
    private ClassTypeDto classType;
}
