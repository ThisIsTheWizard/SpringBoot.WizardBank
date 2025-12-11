package com.wizardcloud.wizardbank.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wizardcloud.wizardbank.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
}