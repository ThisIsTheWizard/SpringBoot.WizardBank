package com.wizardcloud.wizardbank.controllers;

import jakarta.validation.Valid;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wizardcloud.wizardbank.DTO.*;

import com.wizardcloud.wizardbank.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    private ResponseEntity<UserResponse> getUser(@PathVariable UUID id) {
        UserResponse user = userService.getUser(id);

        return ResponseEntity.ok(user);
    }

    @GetMapping("")
    private ResponseEntity<Page<UserResponse>> getUsers(
        @PageableDefault(
            direction = Sort.Direction.ASC,
            size = 50,
            sort = {"firstName", "lastName"}
        ) Pageable pageable
    ) {
        Page<UserResponse> pageUsers = userService.getUsers(pageable);

        return ResponseEntity.ok(pageUsers);
    }

    @PostMapping("")
    private ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreationInput body) {
        UserResponse createdUser = userService.createUser(body);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<UserResponse> updateUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateInput userUpdateInput) {
        UserResponse updatedUser = userService.updateUser(id, userUpdateInput);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<UserResponse> deleteUser(@PathVariable UUID id) {
        UserResponse deletedUser = userService.deleteUser(id);

        return ResponseEntity.ok(deletedUser);
    }
}