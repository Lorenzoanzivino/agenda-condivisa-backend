// user-service/src/main/java/com/catalog/userservice/controller/UserGroupMappingController.java
package com.catalog.userservice.controller;

import com.catalog.userservice.dto.UserGroupMappingResponseDto;
import com.catalog.userservice.service.UserGroupMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
@RequiredArgsConstructor
public class UserGroupMappingController {

    private final UserGroupMappingService mappingService;

    @GetMapping("/groups/{groupId}/users")
    public ResponseEntity<List<UserGroupMappingResponseDto>> getUsersByGroupId(@PathVariable String groupId) {
        List<UserGroupMappingResponseDto> response = mappingService.getUsersByGroupId(groupId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}/groups")
    public ResponseEntity<List<UserGroupMappingResponseDto>> getGroupsByUserId(@PathVariable String userId) {
        List<UserGroupMappingResponseDto> response = mappingService.getGroupsByUserId(userId);
        return ResponseEntity.ok(response);
    }
}