package com.catalog.userservice.dto;

import java.time.LocalDate;

public record UserResponseDto(
        String id,
        String email,
        String nome,
        String cognome,
        LocalDate dataNascita,
        String avatar
) {}