package com.catalog.calendarservice.dto;

import jakarta.validation.constraints.NotBlank;

public record InviteRequestDto(
        @NotBlank String eventoId,
        @NotBlank String utenteId,
        @NotBlank String statoRisposta
) {}