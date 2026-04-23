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
    public EventResponseDto createEvent(EventRequestDto request, String userId) {
        EventoEntity entity = eventoMapper.toEntity(request);
        entity.setOrganizzatoreId(userId); // Sovrascrive eventuali dati con l'ID verificato dal Gateway
        return eventoMapper.toDto(eventoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public EventResponseDto getEventById(String id, String userId) {
        EventoEntity evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato"));

        if (!evento.getOrganizzatoreId().equals(userId)) {
            throw new ResourceNotFoundException("Evento non trovato o accesso negato");
        }

        return eventoMapper.toDto(evento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponseDto> getMyEvents(String userId) {
        return eventoRepository.findByOrganizzatoreId(userId).stream()
                .map(eventoMapper::toDto)
                .collect(Collectors.toList());
    }
}