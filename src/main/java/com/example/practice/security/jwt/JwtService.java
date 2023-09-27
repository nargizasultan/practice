package com.example.practice.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtService {

    @Value("${spring.jwt.secret_key}")
    private String SECRET_KEY;


    public String generateToken(String username) {

        Date expirationDate = Date.from(ZonedDateTime.now().plusMonths(1).toInstant());

        return JWT.create().withSubject("User details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("peaksoft")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(SECRET_KEY));


    }

    public String validateToken(String token) {

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .withSubject("User details")
                .withIssuer("peaksoft").build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getClaim("username").asString();


    }

}
