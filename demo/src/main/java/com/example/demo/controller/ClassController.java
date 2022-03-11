package com.example.demo.controller;

import com.example.demo.dto.ClassDto;
import com.example.demo.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class")
public class ClassController {
    @Autowired
    private ClassService classService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ClassDto dto){
        classService.create(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id){
        ClassDto classDto = classService.get(id);
        return ResponseEntity.ok(classDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        List<ClassDto> classDtoList = classService.getAll();
        return ResponseEntity.ok(classDtoList);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ClassDto dto){
        classService.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        classService.delete(id);
        return ResponseEntity.ok().build();
    }
}
