package com.catalog.userservice.service.impl;

import com.catalog.userservice.dto.AuthRequestDto;
import com.catalog.userservice.dto.AuthResponseDto;
import com.catalog.userservice.dto.UserRequestDto;
import com.catalog.userservice.dto.UserResponseDto;
import com.catalog.userservice.entity.UserEntity;
import com.catalog.userservice.exception.DuplicateResourceException;
import com.catalog.userservice.exception.ResourceNotFoundException;
import com.catalog.userservice.mapper.UserMapper;
import com.catalog.userservice.repository.UserRepository;
import com.catalog.userservice.service.JwtService;
import com.catalog.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email già registrata");
        }
        UserEntity entity = userMapper.toEntity(request);
        entity.setPassword(passwordEncoder.encode(request.password()));
        return userMapper.toDto(userRepository.save(entity));
    }

    public AuthResponseDto login(AuthRequestDto request) {
        UserEntity user = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(request.email()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Credenziali non valide"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ResourceNotFoundException("Credenziali non valide");
        }

        String token = jwtService.generateToken(user.getId());
        return new AuthResponseDto(token, userMapper.toDto(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(String id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }
}