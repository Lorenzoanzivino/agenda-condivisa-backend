// user-service/src/main/java/com/catalog/userservice/service/impl/UserServiceImpl.java
package com.catalog.userservice.service.impl;

import com.catalog.userservice.dto.AuthResponseDto;
import com.catalog.userservice.dto.LoginRequestDto;
import com.catalog.userservice.dto.RegisterRequestDto;
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
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AuthResponseDto register(RegisterRequestDto request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email già in uso");
        }

        UserEntity user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        UserEntity savedUser = userRepository.save(user);

        return new AuthResponseDto(
                jwtService.generateToken(savedUser.getId()),
                userMapper.toDto(savedUser)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponseDto login(LoginRequestDto request) {
        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("Credenziali non valide"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ResourceNotFoundException("Credenziali non valide");
        }

        return new AuthResponseDto(
                jwtService.generateToken(user.getId()),
                userMapper.toDto(user)
        );
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