package com.example.authapp.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class JwtService {

    public static final long EXPIRES_IN_SECONDS = 3600L;

    public String generateToken(String subject, String email, String fullName) {
        return Jwt.issuer("authapp-backend")
                .subject(subject)
                .upn(email)
                .groups(Set.of("USER"))
                .claim("email", email)
                .claim("fullName", fullName)
                .expiresIn(Duration.ofSeconds(EXPIRES_IN_SECONDS))
                .sign();
    }
}