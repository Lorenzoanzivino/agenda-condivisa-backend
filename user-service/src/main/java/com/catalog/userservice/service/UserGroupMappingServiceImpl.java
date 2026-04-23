package com.catalog.userservice.service;

import com.catalog.userservice.dto.UserGroupMappingRequestDto;
import com.catalog.userservice.dto.UserGroupMappingResponseDto;
import com.catalog.userservice.entity.GroupEntity;
import com.catalog.userservice.entity.UserEntity;
import com.catalog.userservice.entity.UserGroupMappingEntity;
import com.catalog.userservice.exception.DuplicateResourceException;
import com.catalog.userservice.exception.ResourceNotFoundException;
import com.catalog.userservice.mapper.UserGroupMappingMapper;
import com.catalog.userservice.repository.GroupRepository;
import com.catalog.userservice.repository.UserGroupMappingRepository;
import com.catalog.userservice.repository.UserRepository;
import com.catalog.userservice.service.UserGroupMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserGroupMappingServiceImpl implements UserGroupMappingService {

    private final UserGroupMappingRepository mappingRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserGroupMappingMapper mappingMapper;

    @Override
    @Transactional
    public UserGroupMappingResponseDto addUserToGroup(UserGroupMappingRequestDto request) {
        if (mappingRepository.existsByUserIdAndGroupId(request.userId(), request.groupId())) {
            throw new DuplicateResourceException("L'utente fa già parte di questo gruppo.");
        }

        UserEntity user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));

        GroupEntity group = groupRepository.findById(request.groupId())
                .orElseThrow(() -> new ResourceNotFoundException("Gruppo non trovato"));

        UserGroupMappingEntity mapping = new UserGroupMappingEntity();
        mapping.setUser(user);
        mapping.setGroup(group);
        mapping.setRuolo(request.ruolo());

        UserGroupMappingEntity savedMapping = mappingRepository.save(mapping);
        return mappingMapper.toDto(savedMapping);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserGroupMappingResponseDto> getUsersByGroupId(String groupId) {
        return mappingRepository.findByGroupId(groupId).stream()
                .map(mappingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserGroupMappingResponseDto> getGroupsByUserId(String userId) {
        return mappingRepository.findByUserId(userId).stream()
                .map(mappingMapper::toDto)
                .collect(Collectors.toList());
    }
}