package com.example.authapp.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ApiErrorResponseDTO {

    public LocalDateTime timestamp;
    public int status;
    public String error;
    public String message;
    public String path;
    public List<String> details;

    public ApiErrorResponseDTO() {
    }

    public ApiErrorResponseDTO(LocalDateTime timestamp, int status, String error, String message, String path, List<String> details) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.details = details;
    }
}