// user-service/src/main/java/com/catalog/userservice/mapper/UserGroupMappingMapper.java
package com.catalog.userservice.mapper;

import com.catalog.userservice.dto.UserGroupMappingResponseDto;
import com.catalog.userservice.entity.UserGroupMappingEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, GroupMapper.class})
public interface UserGroupMappingMapper {

    UserGroupMappingResponseDto toDto(UserGroupMappingEntity entity);
}