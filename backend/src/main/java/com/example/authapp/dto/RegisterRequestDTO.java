package com.example.authapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 120, message = "Nome deve ter no máximo 120 caracteres")
    public String fullName;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Size(max = 150, message = "E-mail deve ter no máximo 150 caracteres")
    public String email;

    @NotBlank(message = "Senha é obrigatória")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "A senha deve ter no mínimo 8 caracteres, uma letra maiúscula, uma minúscula e um número"
    )
    public String password;

    @NotBlank(message = "Confirmação de senha é obrigatória")
    public String confirmPassword;

    @Size(max = 8, message = "CEP deve ter no máximo 8 caracteres")
    public String cep;

    @Size(max = 180, message = "Logradouro deve ter no máximo 180 caracteres")
    public String street;

    @Size(max = 20, message = "Número deve ter no máximo 20 caracteres")
    public String number;

    @Size(max = 120, message = "Complemento deve ter no máximo 120 caracteres")
    public String complement;

    @Size(max = 120, message = "Bairro deve ter no máximo 120 caracteres")
    public String neighborhood;

    @Size(max = 120, message = "Cidade deve ter no máximo 120 caracteres")
    public String city;

    @Size(max = 2, message = "UF deve ter no máximo 2 caracteres")
    public String state;

    @Size(max = 1000, message = "Informações adicionais devem ter no máximo 1000 caracteres")
    public String additionalInfo;

    @Size(max = 1000, message = "Observações devem ter no máximo 1000 caracteres")
    public String notes;
}