package com.example.demo.service;

import com.example.demo.dto.CourseDto;
import com.example.demo.exception.CustomGlobalExceptionHandler;
import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    //Main function
    public CourseDto getDto(Long id){
        return modelMapper.map(getEntity(id), CourseDto.class);
    }

    public List<CourseDto> getAll(){
        List<Course> courseList = courseRepository.findAllByDeletedAtNull();
        if(!courseList.isEmpty()){
            List<Course> list = new ArrayList<>();
            for(Course course : courseList){
                if(course.getDeletedAt() == null){
                    list.add(course);
                }
            }
            List<CourseDto> courseDtoList = list.stream().map(course -> modelMapper.map(course, CourseDto.class))
                    .collect(Collectors.toList());
            return courseDtoList;
        }
        throw new CustomGlobalExceptionHandler("Courses list not found");
    }

    public void create(CourseDto courseDtoRequest){
        Course course = modelMapper.map(courseDtoRequest, Course.class);
        course.setCreatedAt(LocalDateTime.now());
        course.setStatus(true);
        courseRepository.save(course);
    }

    public void update(Long id, CourseDto courseDtoRequest){
        Course course = modelMapper.map(courseDtoRequest, Course.class);
        updateSet(course, id);
        courseRepository.save(course);
    }

    public void delete(Long id){
        Course course = getEntity(id);
        course.setDeletedAt(LocalDateTime.now());
        courseRepository.save(course);
    }

    //Secondary function
    public Course getEntity(Long id) {
        Optional<Course> optional = courseRepository.findByIdAndDeletedAtNull(id);
        if(optional.isEmpty()){
            throw new CustomGlobalExceptionHandler("Course with this id not found");
        }
        return optional.get();
    }

    public Course updateSet(Course courseRequest, Long id){
        Course course = getEntity(id);
        courseRequest.setId(id);
        courseRequest.setUpdatedAt(LocalDateTime.now());
        courseRequest.setCreatedAt(course.getCreatedAt());
        courseRequest.setStatus(course.getStatus());
        return courseRequest;
    }
}
