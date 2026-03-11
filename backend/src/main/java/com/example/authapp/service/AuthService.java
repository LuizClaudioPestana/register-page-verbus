package com.example.authapp.service;

import com.example.authapp.dto.AuthResponseDTO;
import com.example.authapp.dto.ForgotPasswordRequestDTO;
import com.example.authapp.dto.ForgotPasswordResponseDTO;
import com.example.authapp.dto.LoginRequestDTO;
import com.example.authapp.dto.RegisterRequestDTO;
import com.example.authapp.dto.ResetPasswordRequestDTO;
import com.example.authapp.dto.UserResponseDTO;
import com.example.authapp.entity.User;
import com.example.authapp.exception.BusinessException;
import com.example.authapp.repository.UserRepository;
import com.example.authapp.security.JwtService;
import com.example.authapp.security.PasswordUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordUtils passwordUtils;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordUtils passwordUtils, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordUtils = passwordUtils;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserResponseDTO register(RegisterRequestDTO request) {
        validatePasswordConfirmation(request.password, request.confirmPassword);

        if (userRepository.existsByEmail(request.email)) {
            throw new BusinessException("Já existe um usuário cadastrado com este e-mail", 409);
        }

        User user = new User();
        user.fullName = request.fullName.trim();
        user.email = request.email.trim().toLowerCase();
        user.passwordHash = passwordUtils.hash(request.password);
        user.cep = sanitizeCep(request.cep);
        user.street = trimToNull(request.street);
        user.number = trimToNull(request.number);
        user.complement = trimToNull(request.complement);
        user.neighborhood = trimToNull(request.neighborhood);
        user.city = trimToNull(request.city);
        user.state = trimToNull(request.state) != null ? request.state.trim().toUpperCase() : null;
        user.additionalInfo = trimToNull(request.additionalInfo);
        user.notes = trimToNull(request.notes);
        user.createdAt = LocalDateTime.now();
        user.updatedAt = LocalDateTime.now();

        userRepository.persist(user);

        return UserResponseDTO.fromEntity(user);
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.email)
                .orElseThrow(() -> new BusinessException("E-mail ou senha inválidos", 401));

        if (!passwordUtils.matches(request.password, user.passwordHash)) {
            throw new BusinessException("E-mail ou senha inválidos", 401);
        }

        String token = jwtService.generateToken(
                String.valueOf(user.id),
                user.email,
                user.fullName
        );

        return new AuthResponseDTO(
                token,
                "Bearer",
                JwtService.EXPIRES_IN_SECONDS,
                UserResponseDTO.fromEntity(user)
        );
    }

    @Transactional
    public ForgotPasswordResponseDTO forgotPassword(ForgotPasswordRequestDTO request) {
        User user = userRepository.findByEmail(request.email)
                .orElseThrow(() -> new BusinessException("Nenhum usuário encontrado para o e-mail informado", 404));

        String resetToken = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(15);

        user.resetToken = resetToken;
        user.resetTokenExpiresAt = expiresAt;
        user.updatedAt = LocalDateTime.now();

        return new ForgotPasswordResponseDTO(
                "Token de redefinição gerado com sucesso. Em produção, este token seria enviado por e-mail.",
                resetToken,
                expiresAt.toString()
        );
    }

    @Transactional
    public void resetPassword(ResetPasswordRequestDTO request) {
        validatePasswordConfirmation(request.newPassword, request.confirmNewPassword);

        User user = userRepository.findByEmailAndResetToken(request.email, request.token)
                .orElseThrow(() -> new BusinessException("E-mail ou token de redefinição inválidos", 400));

        if (user.resetTokenExpiresAt == null || user.resetTokenExpiresAt.isBefore(LocalDateTime.now())) {
            throw new BusinessException("O token de redefinição expirou", 400);
        }

        if (passwordUtils.matches(request.newPassword, user.passwordHash)) {
            throw new BusinessException("A nova senha não pode ser igual à senha atual", 400);
        }

        user.passwordHash = passwordUtils.hash(request.newPassword);
        user.resetToken = null;
        user.resetTokenExpiresAt = null;
        user.updatedAt = LocalDateTime.now();
    }

    private void validatePasswordConfirmation(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new BusinessException("Senha e confirmação de senha não conferem", 400);
        }
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String sanitizeCep(String cep) {
        if (cep == null || cep.isBlank()) {
            return null;
        }

        return cep.replaceAll("\\D", "");
    }
}