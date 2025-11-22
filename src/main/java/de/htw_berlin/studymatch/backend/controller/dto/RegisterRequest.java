package de.htw_berlin.studymatch.backend.controller.dto;

import de.htw_berlin.studymatch.backend.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest (
        @NotBlank String vorname,
        @NotBlank String username,
        @NotBlank @Size(min = 6) String password,
        String img,
        Role role
){}
