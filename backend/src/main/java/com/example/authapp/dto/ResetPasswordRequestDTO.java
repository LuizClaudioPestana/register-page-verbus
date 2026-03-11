package com.example.authapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ResetPasswordRequestDTO {

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    public String email;

    @NotBlank(message = "Token é obrigatório")
    public String token;

    @NotBlank(message = "Nova senha é obrigatória")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "A nova senha deve ter no mínimo 8 caracteres, uma letra maiúscula, uma minúscula e um número"
    )
    public String newPassword;

    @NotBlank(message = "Confirmação da nova senha é obrigatória")
    public String confirmNewPassword;
}