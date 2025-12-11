package com.wizardcloud.wizardbank.controllers;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wizardcloud.wizardbank.data_transfer_objects.UserCreationInput;
import com.wizardcloud.wizardbank.data_transfer_objects.UserOutput;

import com.wizardcloud.wizardbank.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    private String getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @GetMapping("")
    private String getUsers(@RequestParam(required = false) String filter) {
        return userService.getUsers(filter);
    }

    @PostMapping("")
    private ResponseEntity<UserOutput> createUser(@Valid @RequestBody UserCreationInput body) {
        UserOutput createdUser = userService.createUser(body);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private String updateUser(@PathVariable String id, @RequestBody Object userPayload) {
        return userService.updateUser(id, userPayload);
    }

    @DeleteMapping("/{id}")
    private String deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }
}