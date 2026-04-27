package com.catalog.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "utenti", schema = "user_schema")
@Getter
@Setter
public class UserEntity {

    @Id
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private String password;

    @Column(name = "data_nascita")
    private LocalDate dataNascita;

    @Column
    private String avatar;
}