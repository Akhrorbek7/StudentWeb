package com.example.demo.controller;

import com.example.demo.dto.RoomDto;
import com.example.demo.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/room")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RoomDto dto) {
        roomService.create(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        RoomDto roomDto = roomService.get(id);
        return ResponseEntity.ok(roomDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        List<RoomDto> roomDtoList = roomService.getAll();
        return ResponseEntity.ok(roomDtoList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody RoomDto dto){
        roomService.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        roomService.delete(id);
        return ResponseEntity.ok().build();
    }
}
