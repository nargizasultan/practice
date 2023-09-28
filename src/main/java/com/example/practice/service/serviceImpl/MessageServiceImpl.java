package com.example.practice.service.serviceImpl;

import com.example.practice.dto.MessageRequest;
import com.example.practice.dto.SimpleResponse;
import com.example.practice.enity.Message;
import com.example.practice.enity.User;
import com.example.practice.exceptions.BadCredentialException;
import com.example.practice.exceptions.NotFoundException;
import com.example.practice.repository.MessageRepository;
import com.example.practice.repository.UserRepository;
import com.example.practice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public SimpleResponse send(Long userId, MessageRequest messageRequest) {

        User user = findUserById(userId);
        User currentUser = currentUser();
        Message message = new Message();
        message.setTo(user);
        message.setFrom(currentUser);
        message.setLocalDateTime(LocalDateTime.now());
        message.setContent(messageRequest.getContent());
        messageRepository.save(message);

        return new SimpleResponse(HttpStatus.OK, "Message with id: "+message.getId()+" successfully sent");
    }

    private User currentUser() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getUserByEmail((currentUser.getName()))
                .orElseThrow(() -> new BadCredentialException("Forbidden for non-registered users"));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id: " + userId + " not found!"));
    }
}
