package com.TechYash_Bit.onlineBookStore.Controller;

import com.TechYash_Bit.onlineBookStore.Dto.RequestUserDto;
import com.TechYash_Bit.onlineBookStore.Dto.ResponseUserDto;
import com.TechYash_Bit.onlineBookStore.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {
    final private UserService userService;

    @PostMapping
    public ResponseEntity<ResponseUserDto> registerUser(@Valid @RequestBody RequestUserDto userDto){
        ResponseUserDto userDto1=userService.registerUser(userDto);
        return new ResponseEntity<>(userDto1,HttpStatus.CREATED);}

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDto> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<ResponseUserDto>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ResponseUserDto> updateUser(@PathVariable long id, @Valid @RequestBody RequestUserDto user){
        return ResponseEntity.ok(userService.updateUser(id,user));
    }


}
