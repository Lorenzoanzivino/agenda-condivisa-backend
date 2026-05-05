package com.catalog.calendarservice.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record NotificationEvent(
        String userId,
        String titoloEvento,
        String messaggio,
        LocalDateTime timestamp
) implements Serializable {
}