package com.wizardcloud.wizardbank.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.*;

import com.wizardcloud.wizardbank.enums.UserStatus;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(nullable = false, updatable = false, unique = true)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(unique = true)
    private String phone_number;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.ACTIVE;

    @Column(nullable = false, unique = true)
    private String username;
}