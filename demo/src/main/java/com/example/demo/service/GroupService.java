package com.example.demo.service;

import com.example.demo.dto.GroupDto;
import com.example.demo.exception.CustomGlobalExceptionHandler;
import com.example.demo.model.Group;
import com.example.demo.repository.GroupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CourseService courseService;

    //Main functions
    public GroupDto getDto(Long id){
        return modelMapper.map(getEntity(id), GroupDto.class);
    }

    public List<GroupDto> getAll(){
        List<Group> groupList = groupRepository.findAllByDeletedAtIsNull();
        if(!groupList.isEmpty()){
            List<Group> list = new ArrayList<>();
            for(Group group : groupList){
                if(group.getDeletedAt() == null){
                    group.setCourse(courseService.getEntity(group.getCourseId()));
                    list.add(group);
                }
            }
            List<GroupDto> groupDtoList = list.stream().map(group -> modelMapper.map(group, GroupDto.class)).toList();
            return groupDtoList;
        }
        throw new CustomGlobalExceptionHandler("Group list not found");
    }

    public void create(GroupDto groupDtoRequest){
        Group group = modelMapper.map(groupDtoRequest, Group.class);
        group.setCreatedAt(LocalDateTime.now());
        group.setStatus(true);
        groupRepository.save(group);
    }

    public void update(Long id, GroupDto groupDtoRequest){
        Group group = modelMapper.map(groupDtoRequest, Group.class);
        updateSet(group, id);
        groupRepository.save(group);
    }

    public void delete(Long id){
        Group group = getEntity(id);
        group.setDeletedAt(LocalDateTime.now());
        groupRepository.save(group);
    }

    //Secondary functions
    public Group getEntity(Long id) {
        Optional<Group> optional = groupRepository.findByIdAndDeletedAtNull(id);
        if(optional.isEmpty() || optional.get().getDeletedAt() != null){
            throw new CustomGlobalExceptionHandler("Group with this id not found");
        }
        optional.get().setCourse(courseService.getEntity(optional.get().getCourseId()));
        return optional.get();
    }

    public Group updateSet(Group groupRequest, Long id){
        Group group = getEntity(id);
        groupRequest.setId(id);
        groupRequest.setUpdatedAt(LocalDateTime.now());
        groupRequest.setCreatedAt(group.getCreatedAt());
        groupRequest.setStatus(group.getStatus());
        return groupRequest;
    }
}
