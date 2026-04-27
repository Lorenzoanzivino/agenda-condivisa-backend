package com.catalog.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserRequestDto(
        @NotBlank(message = "L'email è obbligatoria")
        @Email(message = "Formato email non valido")
        String email,

        @NotBlank(message = "Il nome è obbligatorio")
        @Size(min = 2, max = 50)
        String nome,

        @NotBlank(message = "Il cognome è obbligatorio")
        @Size(min = 2, max = 50)
        String cognome,

        @NotBlank(message = "La password è obbligatoria")
        @Size(min = 8, message = "La password deve avere almeno 8 caratteri")
        String password,

        // Nuovi campi (opzionali in fase di registrazione)
        LocalDate dataNascita,
        String avatar
) {}