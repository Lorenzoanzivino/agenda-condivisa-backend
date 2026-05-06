// agent-service/src/main/java/com/catalog/agentservice/service/ChatService.java
package com.catalog.agentservice.service;

import com.catalog.agentservice.dto.ChatResponseDto;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public ChatResponseDto getResponse(String message, String userId) {
        String content = chatClient.prompt()
                .system(s -> s.text("Sei l'assistente virtuale del sistema 'Agenda Condivisa'. " +
                                "Il tuo compito è aiutare l'utente a gestire eventi privati e condivisi. " +
                                "Sii sintetico e professionale. " +
                                "L'utente attuale ha ID: {userId}")
                        .param("userId", userId))
                .user(message)
                .call()
                .content();

        return new ChatResponseDto(content);
    }
}