package com.example.practice.api;

import com.example.practice.dto.MessageRequest;
import com.example.practice.dto.SimpleResponse;
import com.example.practice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageApi {
    private final MessageService messageService;
    @PostMapping("/{userId}")
    public SimpleResponse sendMessage(@PathVariable Long userId, MessageRequest messageRequest){
        return messageService.send(userId, messageRequest);


    }
}
