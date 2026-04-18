package com.catalog.calendarservice.service;

import com.catalog.calendarservice.dto.InviteRequestDto;
import com.catalog.calendarservice.dto.InviteResponseDto;
import java.util.List;

public interface InviteService {
    InviteResponseDto createInvite(InviteRequestDto request);
    List<InviteResponseDto> getInvitesByEvent(String eventId);
    List<InviteResponseDto> getInvitesByUser(String userId);
}