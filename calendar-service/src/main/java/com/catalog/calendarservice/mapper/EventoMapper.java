package com.catalog.calendarservice.mapper;

import com.catalog.calendarservice.dto.EventRequestDto;
import com.catalog.calendarservice.dto.EventResponseDto;
import com.catalog.calendarservice.entity.EventoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventoMapper {

    EventResponseDto toDto(EventoEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizzatoreId", ignore = true)
        // Settato nel Service
    EventoEntity toEntity(EventRequestDto dto);
}