package com.wizardcloud.wizardbank.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.*;

public class UserCreationInput {
    @Email
    public String email;

    @NotBlank(message = "FIRST_NAME_IS_REQUIRED")
    public String firstName;

    @NotBlank(message = "LAST_NAME_IS_REQUIRED")
    public String lastName;

    @NotBlank(message = "PASSWORD_IS_REQUIRED")
    @Size(min = 6, message = "PASSWORD_MUST_BE_AT_LEAST_SIX_CHARACTERS_LONG")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).*$",
        message = "PASSWORD_DOES_NOT_MEET_SYSTEM_REQUIREMENTS"
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String password;

    @Pattern(
        regexp = "^\\+?[0-9\\-\\s]{7,20}$",
        message = "INVALID_PHONE_NUMBER"
    )
    public String phoneNumber;

    @NotBlank(message = "USERNAME_IS_REQUIRED")
    @Size(min = 3, message = "USERNAME_MUST_BE_AT_LEAST_THREE_CHARACTERS_LONG")
    public String username;
}