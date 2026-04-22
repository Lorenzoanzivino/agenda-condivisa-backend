package com.catalog.agentservice.controller;

import com.catalog.agentservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping
    public String chat(@RequestParam(defaultValue = "Ciao, chi sei e cosa puoi fare?") String message) {
        return chatService.getResponse(message);
    }
}