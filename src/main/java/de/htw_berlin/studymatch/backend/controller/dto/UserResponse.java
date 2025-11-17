package de.htw_berlin.studymatch.backend.controller.dto;

public record UserResponse(
   Long id,
   String vorname,
   String email,
   String img
) {}
