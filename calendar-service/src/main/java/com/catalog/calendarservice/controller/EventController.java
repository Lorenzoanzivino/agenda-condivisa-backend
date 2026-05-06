package com.catalog.calendarservice.controller;

import com.catalog.calendarservice.dto.EventRequestDto;
import com.catalog.calendarservice.dto.EventResponseDto;
import com.catalog.calendarservice.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(
            @Valid @RequestBody EventRequestDto request,
            @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(request, userId));
    }

    @GetMapping("/me") // Per lo swap "Calendario Personale"
    public ResponseEntity<List<EventResponseDto>> getMyPersonalEvents(
            @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(eventService.getPersonalEvents(userId));
    }

    @GetMapping("/group/{groupId}") // Per lo swap "Calendario Condiviso"
    public ResponseEntity<List<EventResponseDto>> getGroupEvents(@PathVariable String groupId) {
        return ResponseEntity.ok(eventService.getGroupEvents(groupId));
    }
}