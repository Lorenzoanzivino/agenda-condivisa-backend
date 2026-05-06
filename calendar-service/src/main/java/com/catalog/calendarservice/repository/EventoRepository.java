package com.catalog.calendarservice.repository;

import com.catalog.calendarservice.entity.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<EventoEntity, String> {
    // Eventi privati (personali)
    List<EventoEntity> findByOrganizzatoreIdAndGruppoIdIsNull(String organizzatoreId);

    // Eventi di un gruppo specifico (condivisi)
    List<EventoEntity> findByGruppoId(String gruppoId);

    // Per lo scheduler delle notifiche
    List<EventoEntity> findByDataInizioBetween(LocalDateTime start, LocalDateTime end);
}