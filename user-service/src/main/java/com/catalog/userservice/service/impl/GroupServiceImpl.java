// user-service/src/main/java/com/catalog/userservice/service/impl/GroupServiceImpl.java
package com.catalog.userservice.service.impl;

import com.catalog.userservice.dto.GroupRequestDto;
import com.catalog.userservice.dto.GroupResponseDto;
import com.catalog.userservice.dto.JoinGroupRequestDto;
import com.catalog.userservice.dto.UserGroupMappingRequestDto;
import com.catalog.userservice.entity.GroupEntity;
import com.catalog.userservice.exception.ResourceNotFoundException;
import com.catalog.userservice.mapper.GroupMapper;
import com.catalog.userservice.repository.GroupRepository;
import com.catalog.userservice.service.GroupService;
import com.catalog.userservice.service.UserGroupMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final UserGroupMappingService mappingService;

    @Override
    @Transactional
    public GroupResponseDto createGroup(GroupRequestDto request, String userId) {
        GroupEntity entity = groupMapper.toEntity(request);
        entity.setCodiceInvito(generateInviteCode());
        GroupEntity savedEntity = groupRepository.save(entity);

        // Il creatore viene aggiunto automaticamente come ADMIN
        mappingService.addUserToGroup(new UserGroupMappingRequestDto(userId, savedEntity.getId(), "ADMIN"));

        return groupMapper.toDto(savedEntity);
    }

    @Override
    @Transactional
    public GroupResponseDto joinGroup(JoinGroupRequestDto request, String userId) {
        GroupEntity group = groupRepository.findByCodiceInvito(request.codiceInvito())
                .orElseThrow(() -> new ResourceNotFoundException("Codice invito non valido"));

        mappingService.addUserToGroup(new UserGroupMappingRequestDto(userId, group.getId(), "MEMBER"));

        return groupMapper.toDto(group);
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
                .toList();
    }

    private String generateInviteCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        while (code.length() < 8) {
            code.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return code.toString();
    }
}