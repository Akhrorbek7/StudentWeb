package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserFilterDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getOne/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id){
        UserDto userDto = userService.getDto(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        List<UserDto> userDtoList = userService.getAll();
        return ResponseEntity.ok(userDtoList);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody UserDto userDto){
        userService.create(userDto);
        return ResponseEntity.ok().body("User created");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable("id") Long id, @RequestBody UserDto userDtoRequest){
        userService.update(userDtoRequest, id);
        return ResponseEntity.ok().body("User updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        userService.delete(id);
        return ResponseEntity.ok().body("UserType deleted");
    }

    @GetMapping("/page")
    public ResponseEntity<?> page(@RequestParam("page") Integer page, @RequestParam("size") Integer size){
        List<UserDto> result = userService.page(page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody UserFilterDto userFilterDto){
        List<UserDto> result = userService.filter(userFilterDto);
        return ResponseEntity.ok(result);
    }
}
