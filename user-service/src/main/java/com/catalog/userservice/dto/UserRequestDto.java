package com.catalog.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
        @NotBlank(message = "L'email è obbligatoria")
        @Email(message = "Formato email non valido")
        String email,

        @NotBlank(message = "Il nome è obbligatorio")
        @Size(min = 2, max = 50, message = "Il nome deve avere tra 2 e 50 caratteri")
        String nome,

        @NotBlank(message = "Il cognome è obbligatorio")
        @Size(min = 2, max = 50, message = "Il cognome deve avere tra 2 e 50 caratteri")
        String cognome
) {}