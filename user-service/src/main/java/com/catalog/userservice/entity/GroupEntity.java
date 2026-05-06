package com.catalog.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "groups", schema = "user_schema")
@Getter
@Setter
@NoArgsConstructor
public class GroupEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "codice_invito", unique = true, nullable = false)
    private String codiceInvito;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}