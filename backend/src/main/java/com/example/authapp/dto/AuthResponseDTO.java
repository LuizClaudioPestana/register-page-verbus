package com.example.authapp.dto;

public class AuthResponseDTO {

    public String accessToken;
    public String tokenType;
    public Long expiresIn;
    public UserResponseDTO user;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(String accessToken, String tokenType, Long expiresIn, UserResponseDTO user) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.user = user;
    }
}