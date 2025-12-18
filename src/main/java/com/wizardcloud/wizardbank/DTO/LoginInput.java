package com.wizardcloud.wizardbank.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginInput {
    @Email
    public String email;

    @NotBlank(message = "PASSWORD_IS_REQUIRED")
    public String password;

    public String username;
}
