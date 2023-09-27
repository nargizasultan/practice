package com.example.practice.security.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.practice.enity.User;
import com.example.practice.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader !=null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(7);
            if(token.isBlank()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT token in Bearer Header");
            }
            else{
                try{
                    String email = jwtService.validateToken(token);
                    User user = userRepository.getUserByEmail(email).orElseThrow(() -> new NoSuchElementException("With email: " + email + " doesn't exists"));
                    UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
                    if(SecurityContextHolder.getContext().getAuthentication()==null){
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }




                }catch (JWTVerificationException e){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid jwt token");

                }
            }
        }
        filterChain.doFilter(request, response);


    }
}
