package com.example.demo.controller;

import com.example.demo.dto.GroupDto;
import com.example.demo.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody GroupDto groupDtoRequest){
        groupService.create(groupDtoRequest);
        return ResponseEntity.ok().body("Group created");
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id){
        GroupDto groupDto = groupService.getDto(id);
        return ResponseEntity.ok(groupDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        List<GroupDto> groupDtoList = groupService.getAll();
        return ResponseEntity.ok(groupDtoList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable("id") Long id, @RequestBody GroupDto groupDtoRequest){
        groupService.update(id, groupDtoRequest);
        return ResponseEntity.ok().body("Group updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        groupService.delete(id);
        return ResponseEntity.ok("Group deleted");
    }
}
