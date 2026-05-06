// user-service/src/main/java/com/catalog/userservice/controller/GroupController.java
package com.catalog.userservice.controller;

import com.catalog.userservice.dto.GroupRequestDto;
import com.catalog.userservice.dto.GroupResponseDto;
import com.catalog.userservice.dto.JoinGroupRequestDto;
import com.catalog.userservice.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupResponseDto> createGroup(
            @Valid @RequestBody GroupRequestDto request,
            @RequestHeader("X-User-Id") String userId) {
        GroupResponseDto response = groupService.createGroup(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/join")
    public ResponseEntity<GroupResponseDto> joinGroup(
            @Valid @RequestBody JoinGroupRequestDto request,
            @RequestHeader("X-User-Id") String userId) {
        GroupResponseDto response = groupService.joinGroup(request, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDto> getGroupById(@PathVariable String id) {
        GroupResponseDto response = groupService.getGroupById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<GroupResponseDto>> getAllGroups() {
        List<GroupResponseDto> response = groupService.getAllGroups();
        return ResponseEntity.ok(response);
    }
}