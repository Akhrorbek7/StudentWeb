package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserFilterDto;
import com.example.demo.exception.CustomGlobalExceptionHandler;
import com.example.demo.model.User;
import com.example.demo.model.UserType;
import com.example.demo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeService userTypeService;

    @Autowired
    private ModelMapper modelMapper;

    //Main functions
    public UserDto getDto(Long id){
        return modelMapper.map(getEntity(id), UserDto.class);
    }

    public List<UserDto> getAll() {
        List<User> userList = userRepository.findAllByDeletedAtNull();
        if(!userList.isEmpty()){
            List<User> list = new ArrayList<>();
            for(User user: userList){
                if(user.getDeletedAt() == null){
                    UserType userType = userTypeService.getEntity(user.getUserTypeId());
                    user.setUserType(userType);
                    list.add(user);
                }
            }
            List<UserDto> userDtoList = list.stream().map(user -> modelMapper.map(user, UserDto.class))
                    .collect(Collectors.toList());
            return userDtoList;
        }
        throw new CustomGlobalExceptionHandler("User list not found");
    }

    public void create(UserDto userDtoRequest){
        User userRequest = modelMapper.map(userDtoRequest, User.class);
        Optional<User> user = userRepository.findByPhoneAndDeletedAtNull(userRequest.getPhone());
        if(user.isPresent()){
            throw new CustomGlobalExceptionHandler("User with this phone already exist");
        }
        userRequest.setStatus(true);
        userRequest.setCreatedAt(LocalDateTime.now());
        userRepository.save(userRequest);
    }

    public void update(UserDto userDtoRequest, Long id){
        User userRequest = modelMapper.map(userDtoRequest, User.class);
        Optional<User> optional = userRepository.findByUserIdAndPhoneAndDeletedAtNull(userRequest.getUserId(), userRequest.getPhone());
        if(optional.isPresent()){
            throw new CustomGlobalExceptionHandler("User with this phone not updated");
        }
        updateSet(userRequest, id);
        userRepository.save(userRequest);
    }

    public void delete(Long id){
        User user = getEntity(id);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public List<UserDto> page(Integer page, Integer size) {
        List<UserDto> userDtoList = new LinkedList<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        for(User user : userPage){
            UserDto userDto = modelMapper.map(user, UserDto.class);
            userDtoList.add(userDto);
        }
        if(userDtoList.isEmpty()){
            throw new CustomGlobalExceptionHandler("Users page not found");
        }
        return userDtoList;
    }

    public List<UserDto> filter(UserFilterDto dto) {
        String sortBy = dto.getSortBy();
        if(sortBy == null){
            sortBy = "createdAt";
        }

        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), dto.getDirection(), sortBy);
        List<Predicate> predicates = new LinkedList<>();
        Specification<User> specification = (((root, query, criteriaBuilder) -> {
            if(dto.getFirstName() != null){
                predicates.add(criteriaBuilder.equal(root.get("firstName"), dto.getFirstName()));
            }
            if(dto.getLastName() != null){
                predicates.add(criteriaBuilder.equal(root.get("lastName"), dto.getLastName()));
            }
            if(dto.getUserTypeId() != null){
                predicates.add(criteriaBuilder.equal(root.get("userTypeId"), dto.getUserTypeId()));
            }
            if(dto.getUsername() != null){
                predicates.add(criteriaBuilder.equal(root.get("username"), dto.getUsername()));
            }
            if(dto.getPhone() != null){
                predicates.add(criteriaBuilder.like(root.get("phone"), dto.getPhone()));
            }
            if(dto.getMinCreatedDate() != null && dto.getMaxCreatedDate() != null){
                predicates.add(criteriaBuilder.between(root.get("createdAt"), dto.getMinCreatedDate(), dto.getMaxCreatedDate()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }));
        Page<User> page = userRepository.findAll(specification, pageable);
        List<UserDto> userDtoList = new LinkedList<>();
        for(User user : page){
            userDtoList.add(modelMapper.map(user, UserDto.class));
        }
        if(userDtoList.isEmpty()){
            throw  new CustomGlobalExceptionHandler("Users not found");
        }
        return userDtoList;
    }

    //Secondary functions
    public User getEntity(Long id){
        Optional<User> optional = userRepository.findById(id);
        if(optional.isEmpty() || optional.get().getDeletedAt() != null){
            throw new CustomGlobalExceptionHandler("User with this id not found");
        }
        optional.get().setUserType(userTypeService.getEntity(optional.get().getUserTypeId()));
        return optional.get();
    }

    public User updateSet(User userRequest, Long id){
        User user = getEntity(id);
        userRequest.setCreatedAt(user.getCreatedAt());
        userRequest.setStatus(user.getStatus());
        userRequest.setId(id);
        userRequest.setUpdatedAt(LocalDateTime.now());
        return userRequest;
    }

}
