package com.catalog.userservice.dto;

public record UserGroupMappingRequestDto(
        String userId,
        String groupId,
        String ruolo
) {}