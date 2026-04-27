package com.catalog.calendarservice.scheduler;

import com.catalog.calendarservice.entity.EventoEntity;
import com.catalog.calendarservice.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    // Esegue il job ogni minuto all'istante zero dei secondi (es: 10:00:00, 10:01:00...)
    // In produzione useremo un cron giornaliero, es: "0 0 8 * * *" (Tutti i giorni alle 8:00)
    @Scheduled(cron = "0 * * * * *")
    public void checkTodayEvents() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        List<EventoEntity> todayEvents = eventoRepository.findByDataInizioBetween(startOfDay, endOfDay);

        log.info("⏰ [SCHEDULER] Ricerca eventi per oggi ({}): trovati {} eventi.", LocalDate.now(), todayEvents.size());

        for (EventoEntity evento : todayEvents) {
            log.info(" 👉 Evento in programma: '{}' alle ore {}", evento.getTitolo(), evento.getDataInizio().toLocalTime());
        }
    }
}