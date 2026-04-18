package com.catalog.userservice.dto;

public record UserGroupMappingResponseDto(
        String id,
        UserResponseDto user,
        GroupResponseDto group,
        String ruolo
) {}