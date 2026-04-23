package com.catalog.userservice.service.impl;

import com.catalog.userservice.dto.GroupRequestDto;
import com.catalog.userservice.dto.GroupResponseDto;
import com.catalog.userservice.entity.GroupEntity;
import com.catalog.userservice.exception.ResourceNotFoundException;
import com.catalog.userservice.mapper.GroupMapper;
import com.catalog.userservice.repository.GroupRepository;
import com.catalog.userservice.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService { // <--- CORRETTO: implementa l'INTERFACCIA

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Override
    @Transactional
    public GroupResponseDto createGroup(GroupRequestDto request) {
        GroupEntity entity = groupMapper.toEntity(request);
        GroupEntity savedEntity = groupRepository.save(entity);
        return groupMapper.toDto(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public GroupResponseDto getGroupById(String id) {
        return groupRepository.findById(id)
                .map(groupMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Gruppo non trovato"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupResponseDto> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(groupMapper::toDto)
                .collect(Collectors.toList());
    }
}