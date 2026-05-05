package com.catalog.calendarservice.repository;

import com.catalog.calendarservice.entity.InvitoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitoRepository extends JpaRepository<InvitoEntity, String> {
    List<InvitoEntity> findByEventoId(String eventoId);

    List<InvitoEntity> findByUtenteId(String utenteId);
}