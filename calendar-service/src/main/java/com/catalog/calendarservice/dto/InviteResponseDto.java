package com.catalog.calendarservice.dto;

public record InviteResponseDto(
        String id,
        String eventoId,
        String utenteId,
        String statoRisposta
) {
}