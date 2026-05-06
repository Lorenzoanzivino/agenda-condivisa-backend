// agent-service/src/main/java/com/catalog/agentservice/controller/ChatController.java
package com.catalog.agentservice.controller;

import com.catalog.agentservice.dto.ChatResponseDto;
import com.catalog.agentservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping
    public ResponseEntity<ChatResponseDto> chat(
            @RequestParam(defaultValue = "Ciao, chi sei e cosa puoi fare?") String message,
            @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(chatService.getResponse(message, userId));
    }
}