package com.example.authapp.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.Map;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class HealthController {

    @GET
    public Map<String, String> home() {
        return Map.of(
                "status", "ok",
                "message", "Backend authapp em execução"
        );
    }
}