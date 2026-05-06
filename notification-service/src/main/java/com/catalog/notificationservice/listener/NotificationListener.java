// notification-service/src/main/java/com/catalog/notificationservice/listener/NotificationListener.java
package com.catalog.notificationservice.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class NotificationListener {

    @RabbitListener(queues = "q.notifications")
    public void handleNotification(Map<String, Object> message) {
        log.info("🔔 [NOTIFICATION SERVICE] Ricevuta nuova notifica dalla coda!");

        // Estrazione sicura dei dati inviati dal Calendar-Service
        String userId = (String) message.get("userId");
        String titolo = (String) message.get("titoloEvento");
        String testo = (String) message.get("messaggio");

        log.info("📩 Destinatario (ID): {}", userId);
        log.info("📅 Evento: {}", titolo);
        log.info("📝 Messaggio: {}", testo);
        log.info("-----------------------------------------------------");
    }
}