package com.catalog.userservice.dto;

import jakarta.validation.constraints.NotBlank;

public record UserGroupMappingRequestDto(
        @NotBlank(message = "L'ID utente è obbligatorio")
        String userId,

        @NotBlank(message = "L'ID gruppo è obbligatorio")
        String groupId,

        @NotBlank(message = "Il ruolo è obbligatorio")
        String ruolo
) {}