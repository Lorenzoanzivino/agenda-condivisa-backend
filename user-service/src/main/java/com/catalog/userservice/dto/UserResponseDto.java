package com.catalog.userservice.dto;

public record UserResponseDto(
        String id,
        String email,
        String nome
) {
}