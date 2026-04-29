package com.catalog.userservice.service.impl;

import com.catalog.userservice.dto.UserRequestDto;
import com.catalog.userservice.dto.UserResponseDto;
import com.catalog.userservice.entity.UserEntity;
import com.catalog.userservice.mapper.UserMapper;
import com.catalog.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testCreateUser_Success() {
        UserRequestDto request = new UserRequestDto("test@test.com", "Nome", "Cognome", "password123", LocalDate.of(1995, 10, 25), "avatar.png");

        UserEntity entity = new UserEntity();
        entity.setId("id-1");
        entity.setEmail("test@test.com");

        UserResponseDto responseDto = new UserResponseDto("id-1", "test@test.com", "Nome", "Cognome", LocalDate.of(1995, 10, 25), "avatar.png");

        // Use lenient() to suppress UnnecessaryStubbingException
        lenient().when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        lenient().when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword123");
        lenient().when(userMapper.toEntity(any(UserRequestDto.class))).thenReturn(entity);
        lenient().when(userRepository.save(any(UserEntity.class))).thenReturn(entity);
        lenient().when(userMapper.toDto(any(UserEntity.class))).thenReturn(responseDto);

        // Ensure this method name matches your actual implementation (e.g., register vs createUser)
        UserResponseDto result = userService.createUser(request);

        assertNotNull(result);
        assertEquals("test@test.com", result.email());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {
        UserRequestDto request = new UserRequestDto("test@test.com", "Nome", "Cognome", "password123", LocalDate.of(1995, 10, 25), "avatar.png");
        UserEntity existingEntity = new UserEntity();

        // Use lenient() here as well
        lenient().when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(existingEntity));

        assertThrows(RuntimeException.class, () -> userService.createUser(request));
        verify(userRepository, never()).save(any(UserEntity.class));
    }
}