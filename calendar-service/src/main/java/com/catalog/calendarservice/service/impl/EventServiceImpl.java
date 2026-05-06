package com.catalog.calendarservice.service.impl;

import com.catalog.calendarservice.dto.EventRequestDto;
import com.catalog.calendarservice.dto.EventResponseDto;
import com.catalog.calendarservice.entity.EventoEntity;
import com.catalog.calendarservice.exception.ResourceNotFoundException;
import com.catalog.calendarservice.mapper.EventoMapper;
import com.catalog.calendarservice.repository.EventoRepository;
import com.catalog.calendarservice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventoRepository eventoRepository;
    private final EventoMapper eventoMapper;

    @Override
    @Transactional
    public EventResponseDto createEvent(EventRequestDto request, String userId) {
        EventoEntity entity = eventoMapper.toEntity(request);
        entity.setOrganizzatoreId(userId);
        return eventoMapper.toDto(eventoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponseDto> getPersonalEvents(String userId) {
        return eventoRepository.findByOrganizzatoreIdAndGruppoIdIsNull(userId).stream()
                .map(eventoMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponseDto> getGroupEvents(String groupId) {
        return eventoRepository.findByGruppoId(groupId).stream()
                .map(eventoMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EventResponseDto getEventById(String id, String userId) {
        return eventoRepository.findById(id)
                .map(eventoMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato"));
    }
}