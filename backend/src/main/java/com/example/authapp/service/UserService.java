package com.example.authapp.service;

import com.example.authapp.dto.UserResponseDTO;
import com.example.authapp.entity.User;
import com.example.authapp.exception.BusinessException;
import com.example.authapp.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuário autenticado não encontrado", 404));

        return UserResponseDTO.fromEntity(user);
    }
}