package com.example.authapp.controller;

import com.example.authapp.dto.UserResponseDTO;
import com.example.authapp.service.UserService;
import io.quarkus.security.Authenticated;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserService userService;
    private final JsonWebToken jwt;

    public UserController(UserService userService, JsonWebToken jwt) {
        this.userService = userService;
        this.jwt = jwt;
    }

    @GET
    @Path("/me")
    @Authenticated
    public UserResponseDTO me() {
        String email = jwt.getClaim("email");
        return userService.getByEmail(email);
    }
}