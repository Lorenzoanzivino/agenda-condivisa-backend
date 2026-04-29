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

import static org.junit.jupiter.api.Assertions.*;
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

        // Rimosso "org-1" per allinearsi ai 4 argomenti richiesti
        EventRequestDto request = new EventRequestDto("Titolo", "Descrizione", dataInizio, dataFine);

        EventoEntity entity = new EventoEntity();
        entity.setId("evt-1");
        entity.setTitolo("Titolo");
        entity.setOrganizzatoreId("org-1");

        // Rimosso parametro in eccesso se EventResponseDto ne richiede meno.
        // Adatta questi campi se il tuo record differisce.
        EventResponseDto responseDto = new EventResponseDto("evt-1", "Titolo", "Descrizione", dataInizio, dataFine, "org-1");

        when(eventoMapper.toEntity(any(EventRequestDto.class))).thenReturn(entity);
        when(eventoRepository.save(any(EventoEntity.class))).thenReturn(entity);
        when(eventoMapper.toDto(any(EventoEntity.class))).thenReturn(responseDto);

        // Se il tuo metodo richiede esplicitamente l'ID organizzatore (es. createEvent(request, userId)), passalo qui
        EventResponseDto result = eventService.createEvent(request, "org-1");

        assertNotNull(result);
        assertEquals("Titolo", result.titolo());
        verify(eventoRepository, times(1)).save(any(EventoEntity.class));
    }
}