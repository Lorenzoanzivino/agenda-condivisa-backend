package com.catalog.calendarservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "inviti", schema = "calendar_schema") // <-- SCHEMA AGGIUNTO
@Getter
@Setter
@NoArgsConstructor
public class InvitoEntity {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id")
    private EventoEntity evento;

    @Column(name = "utente_id", nullable = false)
    private String utenteId;

    @Column(name = "stato_risposta", nullable = false)
    private String statoRisposta;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}