package com.wizardcloud.wizardbank.data_transfer_objects;

import java.util.UUID;

import com.wizardcloud.wizardbank.enums.UserStatus;

public class UserOutput {
    public UUID id;
    public String email;
    public String first_name;
    public String last_name;
    public String phone_number;
    public UserStatus status;
    public String username;
}