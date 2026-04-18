package com.catalog.userservice.controller;

import com.catalog.userservice.dto.UserGroupMappingRequestDto;
import com.catalog.userservice.dto.UserGroupMappingResponseDto;
import com.catalog.userservice.service.UserGroupMappingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
@RequiredArgsConstructor
public class UserGroupMappingController {

    private final UserGroupMappingService mappingService;

    @PostMapping
    public ResponseEntity<UserGroupMappingResponseDto> addUserToGroup(@Valid @RequestBody UserGroupMappingRequestDto request) {
        UserGroupMappingResponseDto response = mappingService.addUserToGroup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

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