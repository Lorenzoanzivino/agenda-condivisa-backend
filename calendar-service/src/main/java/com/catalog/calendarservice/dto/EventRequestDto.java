package com.catalog.calendarservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record EventRequestDto(
        @NotBlank String titolo,
        String descrizione,
        @NotNull LocalDateTime dataInizio,
        @NotNull LocalDateTime dataFine,
        @NotBlank String organizzatoreId
) {}