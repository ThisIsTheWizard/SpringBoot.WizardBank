package com.wizardcloud.wizardbank.services;

import java.util.Locale;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wizardcloud.wizardbank.DTO.*;

import com.wizardcloud.wizardbank.entities.UserEntity;

import com.wizardcloud.wizardbank.mappers.UserMapper;

import com.wizardcloud.wizardbank.repositories.UserRepository;

import com.wizardcloud.wizardbank.utils.HashingUtil;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getUser(UUID id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("USER_NOT_FOUND"));

        return UserMapper.INSTANCE.toUserOutput(user);
    }

    public Page<UserResponse> getUsers(Pageable pageable) {
        Page<UserEntity> usersPage = userRepository.findAll(pageable);

        return usersPage.map(UserMapper.INSTANCE::toUserOutput);
    }

    public UserResponse createUser(UserCreationInput userCreationInput) {
        UserEntity user = UserMapper.INSTANCE.toUserEntity(userCreationInput);

        UserEntity existingUserWithEmail = userRepository.findByEmail(user.getEmail());
        if (existingUserWithEmail != null) {
            throw new IllegalArgumentException("THIS_EMAIL_IS_ALREADY_ASSOCIATED_WITH_AN_EXISTING_USER");
        }

        UserEntity existingUserWithUsername = userRepository.findByUsername(user.getUsername());
        if (existingUserWithUsername != null) {
            throw new IllegalArgumentException("THIS_USERNAME_IS_ALREADY_ASSOCIATED_WITH_AN_EXISTING_USER");
        }

        user.setPassword(HashingUtil.hashPassword(userCreationInput.password));

        user.setUsername(userCreationInput.username);

        userRepository.save(user);

        return UserMapper.INSTANCE.toUserOutput(user);
    }

    public UserResponse updateUser(UUID id, UserUpdateInput userUpdateInput) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("USER_NOT_FOUND"));

        // Email
        if (userUpdateInput.email != null) {
            String email = userUpdateInput.email.toLowerCase(Locale.ROOT);

            UserEntity existingUserWithEmail = userRepository.findByEmail(email);
            if (existingUserWithEmail != null) {
                throw new IllegalArgumentException("THIS_EMAIL_IS_ALREADY_ASSOCIATED_WITH_AN_EXISTING_USER");
            }

            user.setEmail(email);
        }
        // FirstName
        if (userUpdateInput.firstName != null) {
            user.setFirstName(userUpdateInput.firstName);
        }
        // LastName
        if (userUpdateInput.lastName != null) {
            user.setLastName(userUpdateInput.lastName);
        }
        // Password
        if (userUpdateInput.password != null) {
            user.setPassword(HashingUtil.hashPassword(userUpdateInput.password));
        }
        // Username
        if (userUpdateInput.username != null) {
            String username = userUpdateInput.username.toLowerCase(Locale.ROOT);

            UserEntity existingUserWithUsername = userRepository.findByUsername(username);
            if (existingUserWithUsername != null) {
                throw new IllegalArgumentException("THIS_USERNAME_IS_ALREADY_ASSOCIATED_WITH_AN_EXISTING_USER");
            }

            user.setUsername(username);
        }


        userRepository.save(user);

        return UserMapper.INSTANCE.toUserOutput(user);
    }

    public UserResponse deleteUser(UUID id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("USER_NOT_FOUND"));

        userRepository.delete(user);

        return UserMapper.INSTANCE.toUserOutput(user);
    }
}