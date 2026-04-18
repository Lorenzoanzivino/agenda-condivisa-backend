package com.catalog.userservice.mapper;

import com.catalog.userservice.dto.GroupRequestDto;
import com.catalog.userservice.dto.GroupResponseDto;
import com.catalog.userservice.entity.GroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupResponseDto toDto(GroupEntity entity);

    @Mapping(target = "id", ignore = true)
    GroupEntity toEntity(GroupRequestDto dto);

}