package de.htw_berlin.studymatch.backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest (
        @NotBlank String username,
        @NotBlank @Size(min = 6) String password
){}
