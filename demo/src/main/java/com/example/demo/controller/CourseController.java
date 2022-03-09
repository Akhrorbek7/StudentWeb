package com.example.demo.controller;

import com.example.demo.dto.CourseDto;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CourseDto courseDtoRequest){
        courseService.create(courseDtoRequest);
        return ResponseEntity.ok().body("Course created");
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id){
        CourseDto courseDto = courseService.getDto(id);
        return ResponseEntity.ok(courseDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        List<CourseDto> courseDtoList = courseService.getAll();
        return ResponseEntity.ok(courseDtoList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable("id") Long id, @RequestBody CourseDto courseDtoRequest){
        courseService.update(id, courseDtoRequest);
        return ResponseEntity.ok().body("Course updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        courseService.delete(id);
        return ResponseEntity.ok("Course deleted");
    }
}
