package com.catalog.calendarservice.dto;

import java.time.LocalDateTime;

public record EventResponseDto(
        String id,
        String titolo,
        String descrizione,
        LocalDateTime dataInizio,
        LocalDateTime dataFine,
        String organizzatoreId
) {
}