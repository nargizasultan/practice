package com.example.practice.service;

import com.example.practice.dto.SignInRequest;
import com.example.practice.dto.SignUpRequest;
import com.example.practice.dto.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    AuthResponse register(SignUpRequest signUpRequest);

    AuthResponse login(SignInRequest signInRequest);
}
