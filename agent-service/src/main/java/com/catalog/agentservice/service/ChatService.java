package com.catalog.agentservice.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String getResponse(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}