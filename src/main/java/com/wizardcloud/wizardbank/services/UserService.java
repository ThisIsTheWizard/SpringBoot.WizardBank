package com.wizardcloud.wizardbank.services;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String getUser(String id) {
        return "User with ID: " + id;
    }

    public String getUsers(String filter) {
        return "List of users, filter: " + filter;
    }

    public String createUser(Object userPayload) {
        return "User created: " + userPayload.toString();
    }

    public String updateUser(String id, Object userPayload) {
        return "User with ID: " + id + " updated with data: " + userPayload.toString();
    }

    public String deleteUser(String id) {
        return "User with ID: " + id + " deleted";
    }
}