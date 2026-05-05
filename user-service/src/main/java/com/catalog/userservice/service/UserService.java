package com.catalog.userservice.service;

import com.catalog.userservice.dto.UserRequestDto;
import com.catalog.userservice.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto request);

    UserResponseDto getUserById(String id);

    List<UserResponseDto> getAllUsers();
}