package com.example.practice.service;

import com.example.practice.dto.SimpleResponse;
import org.springframework.stereotype.Service;

@Service
public interface ApplicationService {

    SimpleResponse follow(Long receiverId);
    SimpleResponse unfollow(Long receiverId);


}
