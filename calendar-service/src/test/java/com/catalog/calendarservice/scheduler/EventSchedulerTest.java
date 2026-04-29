package com.catalog.calendarservice.scheduler;

import com.catalog.calendarservice.config.RabbitMQConfig;
import com.catalog.calendarservice.dto.NotificationEvent;
import com.catalog.calendarservice.entity.EventoEntity;
import com.catalog.calendarservice.repository.EventoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventSchedulerTest {

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private EventScheduler eventScheduler;

    @Test
    void testCheckTodayEvents_WithEvents() {
        EventoEntity evento = new EventoEntity();
        evento.setTitolo("Test Architettura Asincrona");
        evento.setOrganizzatoreId("USER-123");
        evento.setDataInizio(LocalDateTime.now().withHour(18).withMinute(0));

        when(eventoRepository.findByDataInizioBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(evento));

        eventScheduler.checkTodayEvents();

        verify(eventoRepository, times(1)).findByDataInizioBetween(any(), any());
        verify(rabbitTemplate, times(1)).convertAndSend(
                eq(RabbitMQConfig.EXCHANGE),
                eq(RabbitMQConfig.ROUTING_KEY),
                any(NotificationEvent.class)
        );
    }

    @Test
    void testCheckTodayEvents_NoEvents() {
        when(eventoRepository.findByDataInizioBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        eventScheduler.checkTodayEvents();

        verify(eventoRepository, times(1)).findByDataInizioBetween(any(), any());
        verify(rabbitTemplate, never()).convertAndSend(anyString(), anyString(), any(NotificationEvent.class));
    }
}