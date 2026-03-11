package com.example.authapp.controller;

import com.example.authapp.dto.AuthResponseDTO;
import com.example.authapp.dto.ForgotPasswordRequestDTO;
import com.example.authapp.dto.ForgotPasswordResponseDTO;
import com.example.authapp.dto.LoginRequestDTO;
import com.example.authapp.dto.RegisterRequestDTO;
import com.example.authapp.dto.ResetPasswordRequestDTO;
import com.example.authapp.dto.UserResponseDTO;
import com.example.authapp.service.AuthService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @POST
    @Path("/register")
    public Response register(@Valid RegisterRequestDTO request) {
        UserResponseDTO response = authService.register(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequestDTO request) {
        AuthResponseDTO response = authService.login(request);
        return Response.ok(response).build();
    }

    @POST
    @Path("/forgot-password")
    public Response forgotPassword(@Valid ForgotPasswordRequestDTO request) {
        ForgotPasswordResponseDTO response = authService.forgotPassword(request);
        return Response.ok(response).build();
    }

    @POST
    @Path("/reset-password")
    public Response resetPassword(@Valid ResetPasswordRequestDTO request) {
        authService.resetPassword(request);
        return Response.ok("{\"message\":\"Senha redefinida com sucesso\"}").build();
    }
}