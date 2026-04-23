package com.catalog.agentservice.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String getResponse(String message, String userId) {
        return chatClient.prompt()
                .system(s -> s.text("Sei l'assistente virtuale del sistema 'Agenda Condivisa'. " +
                                "Il tuo compito è aiutare gli utenti a gestire i propri eventi. " +
                                "Attualmente stai parlando con l'utente autenticato avente ID: {userId}")
                        .param("userId", userId))
                .user(message)
                .call()
                .content();
    }
}