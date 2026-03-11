package com.example.authapp.repository;

import com.example.authapp.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public Optional<User> findByEmail(String email) {
        return find("lower(email) = ?1", email.toLowerCase()).firstResultOptional();
    }

    public Optional<User> findByEmailAndResetToken(String email, String resetToken) {
        return find("lower(email) = ?1 and resetToken = ?2", email.toLowerCase(), resetToken)
                .firstResultOptional();
    }

    public boolean existsByEmail(String email) {
        return count("lower(email) = ?1", email.toLowerCase()) > 0;
    }
}