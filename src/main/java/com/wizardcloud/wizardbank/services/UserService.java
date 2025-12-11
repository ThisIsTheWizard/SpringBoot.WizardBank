package com.wizardcloud.wizardbank.services;

import com.wizardcloud.wizardbank.data_transfer_objects.UserCreationInput;
import com.wizardcloud.wizardbank.data_transfer_objects.UserOutput;
import com.wizardcloud.wizardbank.entities.UserEntity;
import com.wizardcloud.wizardbank.mappers.UserMapper;
import com.wizardcloud.wizardbank.repositories.UserRepository;
import com.wizardcloud.wizardbank.utils.HashingUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUser(String id) {
        return "User with ID: " + id;
    }

    public String getUsers(String filter) {
        return "List of users, filter: " + filter;
    }

    public UserOutput createUser(UserCreationInput userCreationInput) {
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

        user.setUsername(userCreationInput.username.trim().toLowerCase());

        userRepository.save(user);

        return UserMapper.INSTANCE.toUserOutput(user);
    }

    public String updateUser(String id, Object userPayload) {
        return "User with ID: " + id + " updated with data: " + userPayload.toString();
    }

    public String deleteUser(String id) {
        return "User with ID: " + id + " deleted";
    }
}