package com.catalog.calendarservice.mapper;

import com.catalog.calendarservice.dto.InviteResponseDto;
import com.catalog.calendarservice.entity.InvitoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvitoMapper {

    @Mapping(target = "eventoId", source = "evento.id")
    InviteResponseDto toDto(InvitoEntity entity);

    // La conversione da InviteRequestDto a Entity verrà gestita nel Service
    // per recuperare l'istanza corretta di EventoEntity dal database.
}