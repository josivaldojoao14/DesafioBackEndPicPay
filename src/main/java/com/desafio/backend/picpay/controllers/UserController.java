package com.desafio.backend.picpay.controllers;


import com.desafio.backend.picpay.domain.user.User;
import com.desafio.backend.picpay.dtos.UserDto;
import com.desafio.backend.picpay.services.impl.UserSeviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/users"))
public class UserController {
    @Autowired
    private UserSeviceImpl userSeviceImpl;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto){
        User newUser = this.userSeviceImpl.createUser(userDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers(){
        List<User> usersList = this.userSeviceImpl.getAllUsers();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }
}
