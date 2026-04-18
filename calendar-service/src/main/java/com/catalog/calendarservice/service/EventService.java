package com.catalog.calendarservice.service;

import com.catalog.calendarservice.dto.EventRequestDto;
import com.catalog.calendarservice.dto.EventResponseDto;
import java.util.List;

public interface EventService {
    EventResponseDto createEvent(EventRequestDto request);
    EventResponseDto getEventById(String id);
    List<EventResponseDto> getEventsByOrganizer(String organizerId);
}