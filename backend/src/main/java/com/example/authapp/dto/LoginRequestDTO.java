package com.example.authapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    public String email;

    @NotBlank(message = "Senha é obrigatória")
    public String password;
}