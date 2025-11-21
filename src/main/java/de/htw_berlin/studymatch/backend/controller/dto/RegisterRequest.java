package de.htw_berlin.studymatch.backend.controller.dto;

public record RegisterRequest (
        String vorname,
        String email,
        String password,
        String img
){}
