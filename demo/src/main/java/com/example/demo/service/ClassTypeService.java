package com.example.demo.service;

import com.example.demo.dto.ClassTypeDto;
import com.example.demo.exception.CustomGlobalExceptionHandler;
import com.example.demo.model.ClassType;
import com.example.demo.repository.ClassTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassTypeService {
    @Autowired
    private ClassTypeRepository classTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    //Main functions
    public ClassTypeDto get(Long id){
        return modelMapper.map(getEntity(id), ClassTypeDto.class);
    }

    public List<ClassTypeDto> getAll(){
        List<ClassType> classTypeList = classTypeRepository.findAllAndDeletedAtNull();
        List<ClassType> list = new LinkedList<>();
        if(!classTypeList.isEmpty()){
            for (ClassType classType : classTypeList){
                if(classType.getDeletedAt() == null){
                    list.add(classType);
                }
            }
            return list.stream().map(classType -> modelMapper.map(classType, ClassTypeDto.class)).toList();
        }
        throw new CustomGlobalExceptionHandler("Class Type List not found");
    }

    public void create(ClassTypeDto dto){
        ClassType classType = modelMapper.map(dto, ClassType.class);
        classType.setCreatedAt(LocalDateTime.now());
        classType.setStatus(true);
        classTypeRepository.save(classType);
    }

    public void update(Long id, ClassTypeDto dto){
        ClassType classType = modelMapper.map(dto, ClassType.class);
        updateSet(id, classType);
        classTypeRepository.save(classType);
    }

    public void delete(Long id){
        ClassType classType = getEntity(id);
        classType.setDeletedAt(LocalDateTime.now());
        classTypeRepository.save(classType);
    }

    //Secondary functions
    public ClassType getEntity(Long id) {
        Optional<ClassType> optional = classTypeRepository.findByIdAndDeletedAtNull(id);
        if(optional.isEmpty()){
            throw new CustomGlobalExceptionHandler("Class Type not found");
        }
        return optional.get();
    }

    private void updateSet(Long id, ClassType classType) {
        ClassType old = getEntity(id);
        classType.setId(id);
        classType.setUpdatedAt(LocalDateTime.now());
        classType.setCreatedAt(old.getCreatedAt());
        classType.setStatus(old.getStatus());
    }
}
