package com.example.demo.service;

import com.example.demo.dto.UserTypeDto;
import com.example.demo.exception.CustomGlobalExceptionHandler;
import com.example.demo.model.UserType;
import com.example.demo.repository.UserTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserTypeService {
    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    //Main functions
    public UserTypeDto getDto(Long id){
        return modelMapper.map(getEntity(id), UserTypeDto.class);
    }

    public List<UserTypeDto> getAll() {
        List<UserType> userTypeList = userTypeRepository.findAllByDeletedAtIsNull();
        if(!userTypeList.isEmpty()){
            List<UserType> list = new ArrayList<>();
            for(UserType userType: userTypeList){
                if(userType.getDeletedAt() == null){
                    list.add(userType);
                }
            }
            List<UserTypeDto> userTypeDtoList = list.stream().map(userType -> modelMapper.map(userType, UserTypeDto.class))
                    .collect(Collectors.toList());
            return userTypeDtoList;
        }
        throw new CustomGlobalExceptionHandler("User type list not found");
    }

    public void create(UserTypeDto userTypeDtoRequest){
        UserType userType = modelMapper.map(userTypeDtoRequest, UserType.class);
        userType.setCreatedAt(LocalDateTime.now());
        userType.setStatus(true);
        userTypeRepository.save(userType);
    }

    public void update(UserTypeDto userTypeDtoRequest, Long id){
        UserType userType = modelMapper.map(userTypeDtoRequest, UserType.class);
        updateSet(userType, id);
        userTypeRepository.save(userType);
    }

    public void delete(Long id){
        UserType userType = getEntity(id);
        userType.setDeletedAt(LocalDateTime.now());
        userTypeRepository.save(userType);
    }

    //Secondary functions
    public UserType getEntity(Long id){
        Optional<UserType> optional = userTypeRepository.findByIdAndDeletedAtIsNull(id);
        if(optional.isEmpty()){
            throw new CustomGlobalExceptionHandler("User type with this id not found");
        }
        return optional.get();
    }

    public UserType updateSet(UserType userTypeRequest, Long id){
        UserType userType = getEntity(id);
        userTypeRequest.setCreatedAt(userType.getCreatedAt());
        userTypeRequest.setStatus(userType.getStatus());
        userTypeRequest.setId(id);
        userTypeRequest.setUpdatedAt(LocalDateTime.now());
        return userTypeRequest;
    }
}
