package com.catalog.userservice.service;

import com.catalog.userservice.dto.UserRequestDto;
import com.catalog.userservice.dto.UserResponseDto;
import com.catalog.userservice.entity.UserEntity;
import com.catalog.userservice.exception.DuplicateResourceException;
import com.catalog.userservice.exception.ResourceNotFoundException;
import com.catalog.userservice.mapper.UserMapper;
import com.catalog.userservice.repository.UserRepository;
import com.catalog.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Utente con questa email già esistente.");
        }
        UserEntity entity = userMapper.toEntity(request);
        UserEntity savedEntity = userRepository.save(entity);
        return userMapper.toDto(savedEntity);
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
                .collect(Collectors.toList());
    }
}