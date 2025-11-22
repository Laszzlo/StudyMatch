package de.htw_berlin.studymatch.backend.controller.dto;

import jakarta.validation.constraints.NotNull;

public record LoginRequest (
        @NotNull String username,
        @NotNull String password
){}
