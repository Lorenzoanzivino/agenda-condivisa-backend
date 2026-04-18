package com.catalog.userservice.dto;

public record UserRequestDto(
        String email,
        String nome,
        String cognome
) {}
