package com.example.demo.controller;

import com.example.demo.dto.UserTypeDto;
import com.example.demo.model.UserType;
import com.example.demo.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user-type")
public class UserTypeController {
    @Autowired
    private UserTypeService userTypeService;

    @GetMapping("/getOne/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id){
        UserTypeDto userTypeDto = userTypeService.getDto(id);
        return ResponseEntity.ok(userTypeDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        List<UserTypeDto> userTypeDtoList = userTypeService.getAll();
        return ResponseEntity.ok(userTypeDtoList);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid UserTypeDto userTypeDtoRequest){
        userTypeService.create(userTypeDtoRequest);
        return ResponseEntity.ok().body("UserType created");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid UserTypeDto userTypeDtoRequest){
        userTypeService.update(userTypeDtoRequest, id);
        return ResponseEntity.ok().body("User Type updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        userTypeService.delete(id);
        return ResponseEntity.ok().body("UserType deleted");
    }
}
