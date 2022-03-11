package com.example.demo.controller;


import com.example.demo.dto.ClassTypeDto;
import com.example.demo.service.ClassTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class-type")
public class ClassTypeController {
    private final ClassTypeService classTypeService;

    public ClassTypeController(ClassTypeService classTypeService) {
        this.classTypeService = classTypeService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ClassTypeDto dto){
        classTypeService.create(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id){
        ClassTypeDto classTypeDto = classTypeService.get(id);
        return ResponseEntity.ok(classTypeDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        List<ClassTypeDto> roomDtoList = classTypeService.getAll();
        return ResponseEntity.ok(roomDtoList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ClassTypeDto dto){
        classTypeService.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        classTypeService.delete(id);
        return ResponseEntity.ok().build();
    }
}