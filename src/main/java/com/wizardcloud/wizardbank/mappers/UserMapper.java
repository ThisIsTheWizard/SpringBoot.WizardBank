package com.wizardcloud.wizardbank.mappers;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

import com.wizardcloud.wizardbank.DTO.UserCreationInput;
import com.wizardcloud.wizardbank.DTO.UserResponse;

import com.wizardcloud.wizardbank.entities.UserEntity;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toUserOutput(UserEntity entity);

    List<UserResponse> toUsersOutput(List<UserEntity> entities);

    UserEntity toUserEntity(UserCreationInput input);
}
