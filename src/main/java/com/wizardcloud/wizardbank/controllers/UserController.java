package com.wizardcloud.wizardbank.controllers;

import com.wizardcloud.wizardbank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public String getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @GetMapping("")
    public String getUsers(@RequestParam (required = false) String filter) {
        return userService.getUsers(filter);
    }

    @PostMapping("")
    public String createUser(@RequestBody Object userPayload) {
        return userService.createUser(userPayload);
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable String id, @RequestBody Object userPayload) {
        return userService.updateUser(id, userPayload);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }
}