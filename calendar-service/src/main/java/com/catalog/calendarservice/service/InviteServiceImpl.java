package com.catalog.calendarservice.service.impl;

import com.catalog.calendarservice.dto.InviteRequestDto;
import com.catalog.calendarservice.dto.InviteResponseDto;
import com.catalog.calendarservice.entity.EventoEntity;
import com.catalog.calendarservice.entity.InvitoEntity;
import com.catalog.calendarservice.exception.ResourceNotFoundException;
import com.catalog.calendarservice.mapper.InvitoMapper;
import com.catalog.calendarservice.repository.EventoRepository;
import com.catalog.calendarservice.repository.InvitoRepository;
import com.catalog.calendarservice.service.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InviteServiceImpl implements InviteService {

    private final InvitoRepository invitoRepository;
    private final EventoRepository eventoRepository;
    private final InvitoMapper invitoMapper;

    @Override
    @Transactional
    public InviteResponseDto createInvite(InviteRequestDto request) {
        EventoEntity evento = eventoRepository.findById(request.eventoId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato"));

        InvitoEntity entity = new InvitoEntity();
        entity.setEvento(evento);
        entity.setUtenteId(request.utenteId());
        entity.setStatoRisposta(request.statoRisposta());

        return invitoMapper.toDto(invitoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InviteResponseDto> getInvitesByEvent(String eventId) {
        return invitoRepository.findByEventoId(eventId).stream()
                .map(invitoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InviteResponseDto> getInvitesByUser(String userId) {
        return invitoRepository.findByUtenteId(userId).stream()
                .map(invitoMapper::toDto)
                .collect(Collectors.toList());
    }
}