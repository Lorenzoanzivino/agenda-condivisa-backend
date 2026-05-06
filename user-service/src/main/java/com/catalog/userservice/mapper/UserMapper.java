// user-service/src/main/java/com/catalog/userservice/mapper/UserMapper.java
package com.catalog.userservice.mapper;

import com.catalog.userservice.dto.RegisterRequestDto;
import com.catalog.userservice.dto.UserResponseDto;
import com.catalog.userservice.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
        // Gestita dal service (BCrypt)
    UserEntity toEntity(RegisterRequestDto dto);
}