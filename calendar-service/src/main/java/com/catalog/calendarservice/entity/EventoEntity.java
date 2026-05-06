package com.catalog.calendarservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "eventi", schema = "calendar_schema")
@Getter
@Setter
@NoArgsConstructor
public class EventoEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String titolo;

    private String descrizione;

    @Column(name = "data_inizio", nullable = false)
    private LocalDateTime dataInizio;

    @Column(name = "data_fine")
    private LocalDateTime dataFine;

    @Column(name = "tutto_giorno")
    private boolean tuttoGiorno;

    @Column(name = "organizzatore_id", nullable = false)
    private String organizzatoreId;

    @Column(name = "gruppo_id")
    private String gruppoId;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}