package com.catalog.userservice.dto;

import jakarta.validation.constraints.NotBlank;

public record JoinGroupRequestDto(
        @NotBlank(message = "Il codice invito è obbligatorio")
        String codiceInvito
) {
}