// user-service/src/main/java/com/catalog/userservice/service/GroupService.java
package com.catalog.userservice.service;

import com.catalog.userservice.dto.GroupRequestDto;
import com.catalog.userservice.dto.GroupResponseDto;
import com.catalog.userservice.dto.JoinGroupRequestDto;

import java.util.List;

public interface GroupService {
    GroupResponseDto createGroup(GroupRequestDto request, String userId);

    GroupResponseDto joinGroup(JoinGroupRequestDto request, String userId);

    GroupResponseDto getGroupById(String id);

    List<GroupResponseDto> getAllGroups();
}