package com.catalog.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GroupRequestDto(
        @NotBlank(message = "Il nome del gruppo è obbligatorio")
        @Size(min = 3, max = 50, message = "Il nome del gruppo deve avere tra 3 e 50 caratteri")
        String nome
) {}