package com.example.practice.service.serviceImpl;

import com.example.practice.dto.AuthResponse;
import com.example.practice.dto.SignInRequest;
import com.example.practice.dto.SignUpRequest;
import com.example.practice.enity.User;
import com.example.practice.exceptions.AlreadyExistsException;
import com.example.practice.exceptions.BadCredentialException;
import com.example.practice.exceptions.InvalidPasswordException;
import com.example.practice.repository.UserRepository;
import com.example.practice.security.jwt.JwtService;
import com.example.practice.service.AuthenticationService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthResponse register(SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())){
            throw new AlreadyExistsException("User with email: "+signUpRequest.getEmail()+" already exists");
        }
        User user=new User();

        user.setName(signUpRequest.getName());
        user.setAge(signUpRequest.getAge());
        user.setRole(signUpRequest.getRole());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(user.getEmail(),token ,user.getRole() );


    }

    @Override
    public AuthResponse login(SignInRequest signInRequest) {
        User user = userRepository.getUserByEmail(signInRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User with email: " + signInRequest.getEmail() + " not found"));
        if(signInRequest.getEmail().isBlank()){
            throw new BadCredentialException("Email is blank!");
        }
        if(!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Incorrect password");
        }
        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(user.getEmail(),token ,user.getRole() );
    }
}
