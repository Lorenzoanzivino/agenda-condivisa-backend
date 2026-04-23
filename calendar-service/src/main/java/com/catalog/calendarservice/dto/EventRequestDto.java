package com.catalog.calendarservice.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record EventRequestDto(
        @NotBlank(message = "Il titolo è obbligatorio")
        String titolo,

        String descrizione,

        @NotNull(message = "La data di inizio è obbligatoria")
        @Future(message = "La data di inizio deve essere nel futuro")
        LocalDateTime dataInizio,

        @NotNull(message = "La data di fine è obbligatoria")
        @Future(message = "La data di fine deve essere nel futuro")
        LocalDateTime dataFine
) {}