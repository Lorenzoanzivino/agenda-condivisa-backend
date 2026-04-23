package com.catalog.agentservice.controller;

import com.catalog.agentservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping
    public String chat(
            @RequestParam(defaultValue = "Ciao, chi sei e cosa puoi fare?") String message,
            @RequestHeader("X-User-Id") String userId) {
        return chatService.getResponse(message, userId);
    }
}