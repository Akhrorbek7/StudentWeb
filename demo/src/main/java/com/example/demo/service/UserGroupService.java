package com.example.demo.service;

import com.example.demo.dto.UserGroupDto;
import com.example.demo.exception.CustomGlobalExceptionHandler;
import com.example.demo.model.UserGroup;
import com.example.demo.repository.UserGroupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserGroupService {
    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    //Main functions
    public UserGroupDto get(Long id){
        return modelMapper.map(getEntity(id), UserGroupDto.class);
    }

    public List<UserGroupDto> getAll(){
        List<UserGroup> userGroupList = userGroupRepository.findAllByDeletedAtNull();
        List<UserGroup> list = new ArrayList<>();
        if(!userGroupList.isEmpty()){
            for(UserGroup userGroup : userGroupList){
                if(userGroup.getDeletedAt() == null){
                    setId(userGroup);
                    list.add(userGroup);
                }
            }
            List<UserGroupDto> userGroupDtoList = list.stream().map(userGroup -> modelMapper.map(userGroup, UserGroupDto.class))
                    .collect(Collectors.toList());
            return userGroupDtoList;
        }
        throw new CustomGlobalExceptionHandler("User Group list not found");
    }

    public void create(UserGroupDto userGroupDtoRequest){
        UserGroup userGroup = modelMapper.map(userGroupDtoRequest, UserGroup.class);
        userGroup.setCreatedAt(LocalDateTime.now());
        userGroup.setStatus(true);
        userGroupRepository.save(userGroup);
    }

    public void update(Long id, UserGroupDto userGroupDtoRequest){
        UserGroup userGroup = modelMapper.map(userGroupDtoRequest, UserGroup.class);
        updateSet(userGroup, id);
        userGroupRepository.save(userGroup);
    }

    public void delete(Long id){
        UserGroup userGroup = getEntity(id);
        userGroup.setDeletedAt(LocalDateTime.now());
        userGroupRepository.save(userGroup);
    }

    //Secondary functions
    public UserGroup getEntity(Long id) {
        Optional<UserGroup> optional = userGroupRepository.findByIdAndDeletedAtNull(id);
        if(optional.isEmpty()){
            throw new CustomGlobalExceptionHandler("User Group not found");
        }
        setId(optional.get());
        return optional.get();
    }

    public UserGroup setId(UserGroup request){
        request.setUser(userService.getEntity(request.getUserId()));
        request.setGroup(groupService.getEntity(request.getGroupId()));
        return request;
    }

    public UserGroup updateSet(UserGroup userGroupRequest, Long id){
        UserGroup userGroup = getEntity(id);
        userGroupRequest.setId(id);
        userGroupRequest.setUpdatedAt(LocalDateTime.now());
        userGroupRequest.setCreatedAt(userGroup.getCreatedAt());
        userGroupRequest.setStatus(userGroup.getStatus());
        return userGroupRequest;
    }
}
