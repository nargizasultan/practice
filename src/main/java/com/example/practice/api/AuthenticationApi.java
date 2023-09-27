package com.example.practice.api;

import com.example.practice.dto.AuthResponse;
import com.example.practice.dto.SignInRequest;
import com.example.practice.dto.SignUpRequest;
import com.example.practice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationApi {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody SignUpRequest signUpRequest){

        return authenticationService.register(signUpRequest);

    }

    @PostMapping("/login")
    public  AuthResponse login(@RequestBody SignInRequest signInRequest){
        return authenticationService.login(signInRequest);
    }






}
