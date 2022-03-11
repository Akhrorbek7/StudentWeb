package com.example.demo.service;

import com.example.demo.dto.ClassDto;
import com.example.demo.exception.CustomGlobalExceptionHandler;
import com.example.demo.model.Class;
import com.example.demo.repository.ClassRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClassTypeService classTypeService;

    @Autowired
    private RoomService roomService;

    //Main functions
    public ClassDto get(Long id){
        return modelMapper.map(getEntity(id), ClassDto.class);
    }

    public List<ClassDto> getAll() {
        List<Class> classList = classRepository.findAllAndDeletedAtNull();
        List<Class> list = new LinkedList<>();
        if(!classList.isEmpty()){
            for (Class entity : classList){
                setId(entity);
                list.add(entity);
            }
            return list.stream().map(aClass -> modelMapper.map(aClass, ClassDto.class)).toList();
        }
        throw new CustomGlobalExceptionHandler("Class list not found");
    }

    public void create(ClassDto dto){
        Class aClass = modelMapper.map(dto, Class.class);
        aClass.setCreatedAt(LocalDateTime.now());
        aClass.setStatus(true);
        classRepository.save(aClass);
    }

    public void update(Long id, ClassDto dto){
        Class aClass = modelMapper.map(dto, Class.class);
        updateSet(id, aClass);
        classRepository.save(aClass);
    }

    public void delete(Long id){
        Class aClass = getEntity(id);
        aClass.setDeletedAt(LocalDateTime.now());
        classRepository.save(aClass);
    }

    //Secondary functions
    public Class getEntity(Long id) {
        Optional<Class> optional = classRepository.findByIdAndDeletedAtNull(id);
        if(optional.isEmpty()){
           throw new CustomGlobalExceptionHandler("Class not found");
        }
        setId(optional.get());
        return optional.get();
    }

    private void setId(Class entity) {
        entity.setClassType(classTypeService.getEntity(entity.getClassTypeId()));
        entity.setRoom(roomService.getEntity(entity.getRoomId()));
    }

    private void updateSet(Long id, Class aClass) {
        Class entity = getEntity(id);
        aClass.setId(id);
        aClass.setUpdatedAt(LocalDateTime.now());
        aClass.setCreatedAt(entity.getCreatedAt());
        aClass.setStatus(entity.getStatus());
    }
}
