package com.catalog.userservice.dto;

public record AuthResponseDto(
        String token,
        UserResponseDto user
) {
}