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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventoRepository eventoRepository;
    private final EventoMapper eventoMapper;

    @Override
    @Transactional
    public EventResponseDto createEvent(EventRequestDto request) {
        EventoEntity entity = eventoMapper.toEntity(request);
        return eventoMapper.toDto(eventoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public EventResponseDto getEventById(String id) {
        return eventoRepository.findById(id)
                .map(eventoMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponseDto> getEventsByOrganizer(String organizerId) {
        return eventoRepository.findByOrganizzatoreId(organizerId).stream()
                .map(eventoMapper::toDto)
                .collect(Collectors.toList());
    }
}