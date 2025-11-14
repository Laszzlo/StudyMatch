package de.htw_berlin.studymatch.backend.controller.dto;

public record UserRequest(
   Long id,
   String vorname,
   String email,
   String password
) {}
