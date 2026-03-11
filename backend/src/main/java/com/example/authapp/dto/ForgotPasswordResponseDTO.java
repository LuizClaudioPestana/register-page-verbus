package com.example.authapp.dto;

public class ForgotPasswordResponseDTO {

    public String message;
    public String resetToken;
    public String expiresAt;

    public ForgotPasswordResponseDTO() {
    }

    public ForgotPasswordResponseDTO(String message, String resetToken, String expiresAt) {
        this.message = message;
        this.resetToken = resetToken;
        this.expiresAt = expiresAt;
    }
}