package com.example.practice.service;

import com.example.practice.dto.MessageRequest;
import com.example.practice.dto.SimpleResponse;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {

    public SimpleResponse send(Long userId, MessageRequest messageRequest);
}
