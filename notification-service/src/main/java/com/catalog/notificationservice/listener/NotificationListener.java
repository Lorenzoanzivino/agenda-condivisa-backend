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
        log.info("🔔 [NOTIFICATION SERVICE] Ricevuto nuovo messaggio dalla coda!");
        log.info("📩 Dettagli: Utente ID: {}, Messaggio: {}",
                message.get("userId"),
                message.get("messaggio"));
        log.info("-----------------------------------------------------");
    }
}