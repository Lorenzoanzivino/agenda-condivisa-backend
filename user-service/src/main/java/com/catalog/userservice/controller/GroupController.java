package com.catalog.userservice.controller;

import com.catalog.userservice.dto.GroupRequestDto;
import com.catalog.userservice.dto.GroupResponseDto;
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
    public ResponseEntity<GroupResponseDto> createGroup(@Valid @RequestBody GroupRequestDto request) {
        GroupResponseDto response = groupService.createGroup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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