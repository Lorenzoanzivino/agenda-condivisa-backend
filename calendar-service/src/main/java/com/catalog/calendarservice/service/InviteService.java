package com.catalog.calendarservice.service;

import com.catalog.calendarservice.dto.InviteRequestDto;
import com.catalog.calendarservice.dto.InviteResponseDto;
import java.util.List;

public interface InviteService {
    InviteResponseDto createInvite(InviteRequestDto request, String organizerId);
    List<InviteResponseDto> getInvitesByEvent(String eventId, String organizerId);
    List<InviteResponseDto> getMyInvites(String userId);
}