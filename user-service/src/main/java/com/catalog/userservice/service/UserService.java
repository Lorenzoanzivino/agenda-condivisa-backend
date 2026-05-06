// user-service/src/main/java/com/catalog/userservice/service/UserService.java
package com.catalog.userservice.service;

import com.catalog.userservice.dto.AuthResponseDto;
import com.catalog.userservice.dto.LoginRequestDto;
import com.catalog.userservice.dto.RegisterRequestDto;
import com.catalog.userservice.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    AuthResponseDto register(RegisterRequestDto request);

    AuthResponseDto login(LoginRequestDto request);

    UserResponseDto getUserById(String id);

    List<UserResponseDto> getAllUsers();
}