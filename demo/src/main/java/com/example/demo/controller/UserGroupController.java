package com.example.demo.controller;

import com.example.demo.dto.UserGroupDto;
import com.example.demo.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user-group")
public class UserGroupController {
    @Autowired private UserGroupService userGroupService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody UserGroupDto userGroupDtoRequest){
        userGroupService.create(userGroupDtoRequest);
        return ResponseEntity.ok().body("User group created");
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id){
        UserGroupDto userGroupDto = userGroupService.get(id);
        return ResponseEntity.ok(userGroupDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        List<UserGroupDto> userGroupDtoList = userGroupService.getAll();
        return ResponseEntity.ok(userGroupDtoList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable("id") Long id, @RequestBody UserGroupDto userGroupDtoRequest){
        userGroupService.update(id, userGroupDtoRequest);
        return ResponseEntity.ok().body("User Group updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        userGroupService.delete(id);
        return ResponseEntity.ok().body("User Group deleted");
    }
}
