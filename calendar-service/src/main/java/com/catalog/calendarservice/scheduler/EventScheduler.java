package com.catalog.calendarservice.scheduler;

import com.catalog.calendarservice.config.RabbitMQConfig;
import com.catalog.calendarservice.dto.NotificationEvent;
import com.catalog.calendarservice.entity.EventoEntity;
import com.catalog.calendarservice.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventScheduler {

    private final EventoRepository eventoRepository;
    private final RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0 * * * * *") // Ogni minuto
    public void checkTodayEvents() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        List<EventoEntity> todayEvents = eventoRepository.findByDataInizioBetween(startOfDay, endOfDay);

        log.info("⏰ [SCHEDULER] Ricerca eventi per oggi ({}): trovati {} eventi.", LocalDate.now(), todayEvents.size());

        for (EventoEntity evento : todayEvents) {
            NotificationEvent notification = new NotificationEvent(
                    evento.getOrganizzatoreId(),
                    evento.getTitolo(),
                    "Hai un impegno: " + evento.getTitolo() + " alle " + evento.getDataInizio().toLocalTime(),
                    LocalDateTime.now()
            );

            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, notification);
            log.info(" ✉️ Notifica inviata a RabbitMQ per l'evento: {}", evento.getTitolo());
        }
    }
}