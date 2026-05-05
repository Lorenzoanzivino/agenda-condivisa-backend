package com.catalog.userservice.service;

import com.catalog.userservice.dto.GroupRequestDto;
import com.catalog.userservice.dto.GroupResponseDto;

import java.util.List;

public interface GroupService {
    GroupResponseDto createGroup(GroupRequestDto request);

    GroupResponseDto getGroupById(String id);

    List<GroupResponseDto> getAllGroups();
}