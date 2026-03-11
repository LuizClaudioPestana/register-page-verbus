package com.example.authapp.dto;

import com.example.authapp.entity.User;

public class UserResponseDTO {

    public Long id;
    public String fullName;
    public String email;
    public String cep;
    public String street;
    public String number;
    public String complement;
    public String neighborhood;
    public String city;
    public String state;
    public String additionalInfo;
    public String notes;

    public static UserResponseDTO fromEntity(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.id = user.id;
        dto.fullName = user.fullName;
        dto.email = user.email;
        dto.cep = user.cep;
        dto.street = user.street;
        dto.number = user.number;
        dto.complement = user.complement;
        dto.neighborhood = user.neighborhood;
        dto.city = user.city;
        dto.state = user.state;
        dto.additionalInfo = user.additionalInfo;
        dto.notes = user.notes;
        return dto;
    }
}