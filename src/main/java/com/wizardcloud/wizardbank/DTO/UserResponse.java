package com.wizardcloud.wizardbank.DTO;

import java.util.UUID;

import com.wizardcloud.wizardbank.enums.UserStatus;

public class UserResponse {
    public UUID id;
    public String email;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public UserStatus status;
    public String username;
}