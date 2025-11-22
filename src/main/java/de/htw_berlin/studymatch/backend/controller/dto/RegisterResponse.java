package de.htw_berlin.studymatch.backend.controller.dto;

import de.htw_berlin.studymatch.backend.model.Role;

public record RegisterResponse(
        Long id,
        String vorname,
        String username,
        Role role
){}
