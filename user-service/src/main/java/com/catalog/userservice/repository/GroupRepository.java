package com.catalog.userservice.repository;

import com.catalog.userservice.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, String> {
    // Necessario per permettere agli utenti di unirsi a un gruppo esistente
    Optional<GroupEntity> findByCodiceInvito(String codiceInvito);
}