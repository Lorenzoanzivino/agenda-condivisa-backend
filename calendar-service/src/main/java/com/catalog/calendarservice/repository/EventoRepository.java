package com.catalog.calendarservice.repository;

import com.catalog.calendarservice.entity.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<EventoEntity, String> {
    List<EventoEntity> findByOrganizzatoreId(String organizzatoreId);
}