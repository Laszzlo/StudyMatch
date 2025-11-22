package de.htw_berlin.studymatch.backend.controller.dto;

public record AuthResponse(
        String token,
        String vorname,
        String email,
        String role
) {}
