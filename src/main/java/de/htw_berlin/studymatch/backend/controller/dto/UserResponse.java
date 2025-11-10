package de.htw_berlin.studymatch.backend.controller.dto;

public record UserResponse(
   Long uid,
   String vorname,
   String email
) {}
