package com.catalog.userservice.mapper;

import com.catalog.userservice.dto.UserRequestDto;
import com.catalog.userservice.dto.UserResponseDto;
import com.catalog.userservice.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(UserRequestDto dto);

}