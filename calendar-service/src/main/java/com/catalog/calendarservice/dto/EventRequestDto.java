package com.catalog.calendarservice.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record EventRequestDto(
        @NotBlank(message = "Il titolo è obbligatorio")
        String titolo,

        String descrizione,

        @NotNull(message = "La data di inizio è obbligatoria")
        @FutureOrPresent(message = "La data di inizio non può essere nel passato")
        LocalDateTime dataInizio,

        @NotNull(message = "La data di fine è obbligatoria")
        LocalDateTime dataFine,

        @NotBlank(message = "L'ID dell'organizzatore è obbligatorio")
        String organizzatoreId
) {}