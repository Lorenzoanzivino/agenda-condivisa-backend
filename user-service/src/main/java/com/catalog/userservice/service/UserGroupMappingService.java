// user-service/src/main/java/com/catalog/userservice/service/UserGroupMappingService.java
package com.catalog.userservice.service;

import com.catalog.userservice.dto.UserGroupMappingRequestDto;
import com.catalog.userservice.dto.UserGroupMappingResponseDto;

import java.util.List;

public interface UserGroupMappingService {
    UserGroupMappingResponseDto addUserToGroup(UserGroupMappingRequestDto request);

    List<UserGroupMappingResponseDto> getUsersByGroupId(String groupId);

    List<UserGroupMappingResponseDto> getGroupsByUserId(String userId);
}