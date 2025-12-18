package com.wizardcloud.wizardbank.controllers;

import com.wizardcloud.wizardbank.DTO.UserCreationInput;
import com.wizardcloud.wizardbank.DTO.UserResponse;
import com.wizardcloud.wizardbank.DTO.UserUpdateInput;
import com.wizardcloud.wizardbank.enums.UserStatus;
import com.wizardcloud.wizardbank.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID id) {
        UserResponse user = userService.getUser(id);

        return ResponseEntity.ok(user);
    }

    @GetMapping("")
    public ResponseEntity<Page<UserResponse>> getUsers(
        @PageableDefault(
            direction = Sort.Direction.ASC,
            size = 50,
            sort = {"firstName", "lastName"}
        ) Pageable pageable,
        @RequestParam(required = false) List<UserStatus> statuses,
        @RequestParam(required = false) String search
    ) {
        Page<UserResponse> pageUsers = userService.getUsers(pageable, statuses, search);

        return ResponseEntity.ok(pageUsers);
    }

    @PostMapping("")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreationInput body) {
        UserResponse createdUser = userService.createUser(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateInput userUpdateInput) {
        UserResponse updatedUser = userService.updateUser(id, userUpdateInput);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable UUID id) {
        UserResponse deletedUser = userService.deleteUser(id);

        return ResponseEntity.ok(deletedUser);
    }
}