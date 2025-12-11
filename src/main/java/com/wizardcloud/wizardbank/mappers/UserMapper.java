package com.wizardcloud.wizardbank.mappers;

import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

import com.wizardcloud.wizardbank.data_transfer_objects.UserCreationInput;
import com.wizardcloud.wizardbank.data_transfer_objects.UserOutput;

import com.wizardcloud.wizardbank.entities.UserEntity;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserOutput toUserOutput(UserEntity entity);

    UserEntity toUserEntity(UserCreationInput input);
}
