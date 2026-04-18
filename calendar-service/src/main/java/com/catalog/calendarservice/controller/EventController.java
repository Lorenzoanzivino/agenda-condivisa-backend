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
    public ResponseEntity<EventResponseDto> createEvent(@Valid @RequestBody EventRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable String id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<EventResponseDto>> getEventsByOrganizer(@PathVariable String organizerId) {
        return ResponseEntity.ok(eventService.getEventsByOrganizer(organizerId));
    }
}