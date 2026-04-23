package com.catalog.calendarservice.dto;

import jakarta.validation.constraints.NotBlank;

public record InviteRequestDto(
        @NotBlank(message = "L'ID dell'evento è obbligatorio")
        String eventoId,

        @NotBlank(message = "L'ID dell'utente è obbligatorio")
        String utenteId,

        @NotBlank(message = "Lo stato della risposta è obbligatorio")
        String statoRisposta
) {}