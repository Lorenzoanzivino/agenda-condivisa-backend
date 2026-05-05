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

@Service
@RequiredArgsConstructor
public class InviteServiceImpl implements InviteService {

    private final InvitoRepository invitoRepository;
    private final EventoRepository eventoRepository;
    private final InvitoMapper invitoMapper;

    @Override
    @Transactional
    public InviteResponseDto createInvite(InviteRequestDto request, String organizerId) {
        EventoEntity evento = eventoRepository.findById(request.eventoId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato"));

        if (!evento.getOrganizzatoreId().equals(organizerId)) {
            throw new ResourceNotFoundException("Evento non trovato o accesso negato");
        }

        InvitoEntity entity = new InvitoEntity();
        entity.setEvento(evento);
        entity.setUtenteId(request.utenteId());
        entity.setStatoRisposta(request.statoRisposta());

        return invitoMapper.toDto(invitoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InviteResponseDto> getInvitesByEvent(String eventId, String organizerId) {
        EventoEntity evento = eventoRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato"));

        if (!evento.getOrganizzatoreId().equals(organizerId)) {
            throw new ResourceNotFoundException("Evento non trovato o accesso negato");
        }

        return invitoRepository.findByEventoId(eventId).stream()
                .map(invitoMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InviteResponseDto> getMyInvites(String userId) {
        return invitoRepository.findByUtenteId(userId).stream()
                .map(invitoMapper::toDto)
                .toList();
    }
}