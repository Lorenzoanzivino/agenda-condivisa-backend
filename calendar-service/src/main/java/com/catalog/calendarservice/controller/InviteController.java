package com.catalog.calendarservice.controller;

import com.catalog.calendarservice.dto.InviteRequestDto;
import com.catalog.calendarservice.dto.InviteResponseDto;
import com.catalog.calendarservice.service.InviteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/invites")
@RequiredArgsConstructor
public class InviteController {

    private final InviteService inviteService;

    @PostMapping
    public ResponseEntity<InviteResponseDto> createInvite(@Valid @RequestBody InviteRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inviteService.createInvite(request));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<InviteResponseDto>> getInvitesByEvent(@PathVariable String eventId) {
        return ResponseEntity.ok(inviteService.getInvitesByEvent(eventId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InviteResponseDto>> getInvitesByUser(@PathVariable String userId) {
        return ResponseEntity.ok(inviteService.getInvitesByUser(userId));
    }
}