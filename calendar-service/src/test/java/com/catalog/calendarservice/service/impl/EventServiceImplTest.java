package com.catalog.calendarservice.service.impl;

import com.catalog.calendarservice.dto.EventRequestDto;
import com.catalog.calendarservice.dto.EventResponseDto;
import com.catalog.calendarservice.entity.EventoEntity;
import com.catalog.calendarservice.mapper.EventoMapper;
import com.catalog.calendarservice.repository.EventoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private EventoMapper eventoMapper;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void testCreateEvent_Success() {
        LocalDateTime dataInizio = LocalDateTime.now();
        LocalDateTime dataFine = dataInizio.plusHours(1);

        // Aggiornato con 6 parametri: titolo, descrizione, dataInizio, dataFine, tuttoGiorno, gruppoId
        EventRequestDto request = new EventRequestDto(
                "Titolo",
                "Descrizione",
                dataInizio,
                dataFine,
                false,
                null
        );

        EventoEntity entity = new EventoEntity();
        entity.setId("evt-1");
        entity.setTitolo("Titolo");
        entity.setOrganizzatoreId("org-1");
        entity.setTuttoGiorno(false);
        entity.setGruppoId(null);

        // Aggiornato con 8 parametri per EventResponseDto
        EventResponseDto responseDto = new EventResponseDto(
                "evt-1",
                "Titolo",
                "Descrizione",
                dataInizio,
                dataFine,
                false,
                "org-1",
                null
        );

        when(eventoMapper.toEntity(any(EventRequestDto.class))).thenReturn(entity);
        when(eventoRepository.save(any(EventoEntity.class))).thenReturn(entity);
        when(eventoMapper.toDto(any(EventoEntity.class))).thenReturn(responseDto);

        EventResponseDto result = eventService.createEvent(request, "org-1");

        assertNotNull(result);
        assertEquals("Titolo", result.titolo());
        verify(eventoRepository, times(1)).save(any(EventoEntity.class));
    }
}