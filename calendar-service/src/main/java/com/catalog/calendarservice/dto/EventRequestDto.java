package com.catalog.calendarservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EventRequestDto(
        @NotBlank(message = "Il titolo è obbligatorio")
        String titolo,

        String descrizione,

        @NotNull(message = "La data di inizio è obbligatoria")
        LocalDateTime dataInizio,

        LocalDateTime dataFine,

        boolean tuttoGiorno,

        String gruppoId // Se null, l'evento è privato
) {
}