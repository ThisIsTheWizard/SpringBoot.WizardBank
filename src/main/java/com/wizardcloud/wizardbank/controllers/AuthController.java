package com.wizardcloud.wizardbank.controllers;

import com.wizardcloud.wizardbank.DTO.LoginInput;
import com.wizardcloud.wizardbank.DTO.TokenResponse;
import com.wizardcloud.wizardbank.DTO.UserCreationInput;
import com.wizardcloud.wizardbank.DTO.UserResponse;
import com.wizardcloud.wizardbank.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserCreationInput body) {
        // TODO: Add additional registration logic if needed (e.g., sending confirmation email)
        UserResponse user = userService.createUser(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginInput body) {
        Boolean isEmail = body.email != null && body.email.contains("@");
        Boolean isUsername = body.username != null && !body.username.isEmpty();

        if (!(isEmail || isUsername)) {
            throw new IllegalArgumentException("EMAIL_OR_USERNAME_IS_REQUIRED");
        }

        TokenResponse tokenResponse = userService.login(body);

        return ResponseEntity.ok(tokenResponse);
    }

}
