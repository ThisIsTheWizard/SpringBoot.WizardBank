package com.wizardcloud.wizardbank.services;

import com.wizardcloud.wizardbank.DTO.*;
import com.wizardcloud.wizardbank.entities.UserEntity;
import com.wizardcloud.wizardbank.enums.UserStatus;
import com.wizardcloud.wizardbank.exceptions.ResourceNotFoundException;
import com.wizardcloud.wizardbank.mappers.UserMapper;
import com.wizardcloud.wizardbank.repositories.UserRepository;
import com.wizardcloud.wizardbank.utils.HashingUtil;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public UserService(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(UUID id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));

        return UserMapper.INSTANCE.toUserOutput(user);
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getUsers(Pageable pageable, List<UserStatus> statuses, String search) {
        Page<UserEntity> usersPage;

        if (statuses != null || search != null) {
            Specification<UserEntity> spec = createSearchSpecification(statuses, search);

            usersPage = userRepository.findAll(spec, pageable);
        } else {
            usersPage = userRepository.findAll(pageable);
        }

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

        userRepository.save(user);

        return UserMapper.INSTANCE.toUserOutput(user);
    }

    public UserResponse updateUser(UUID id, UserUpdateInput userUpdateInput) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));

        // Email
        if (userUpdateInput.email != null) {
            String email = userUpdateInput.email.toLowerCase(Locale.ROOT);

            if (Objects.equals(user.getEmail(), email)) {
                throw new IllegalArgumentException("EMAIL_IS_SAME_AS_CURRENT");
            }

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

            if (Objects.equals(user.getUsername(), username)) {
                throw new IllegalArgumentException("USERNAME_IS_SAME_AS_CURRENT");
            }

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
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));

        userRepository.delete(user);

        return UserMapper.INSTANCE.toUserOutput(user);
    }

    public TokenResponse login(LoginInput loginInput) {
        UserEntity user;

        if (loginInput.email != null) {
            user = userRepository.findByEmail(loginInput.email.toLowerCase(Locale.ROOT));
        } else {
            user = userRepository.findByUsername(loginInput.username.toLowerCase(Locale.ROOT));
        }
        if (user == null) {
            throw new IllegalArgumentException("INVALID_CREDENTIALS");
        }
        if (!HashingUtil.verifyPassword(loginInput.password, user.getPassword())) {
            throw new IllegalArgumentException("INVALID_CREDENTIALS");
        }
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new IllegalArgumentException("USER_IS_NOT_ACTIVE");
        }

        TokenResponse tokens = new TokenResponse();

        tokens.accessToken = jwtService.generateAccessToken(user);
        tokens.refreshToken = jwtService.generateRefreshToken(user);

        return tokens;
    }

    private Specification<UserEntity> createSearchSpecification(List<UserStatus> statuses, String search) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (search != null) {
                EscapeCharacter escape = EscapeCharacter.DEFAULT;
                String likeSearch = "%" + escape.escape(search.toLowerCase(Locale.ROOT)) + "%";

                predicates.add(
                    criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), likeSearch, escape.getEscapeCharacter()),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), likeSearch, escape.getEscapeCharacter()),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), likeSearch, escape.getEscapeCharacter()),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), likeSearch, escape.getEscapeCharacter()),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), likeSearch, escape.getEscapeCharacter())
                    )
                );
            }
            if (statuses != null && !statuses.isEmpty()) {
                Predicate statusPredicate = root.get("status").in(statuses);
                predicates.add(statusPredicate);
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}