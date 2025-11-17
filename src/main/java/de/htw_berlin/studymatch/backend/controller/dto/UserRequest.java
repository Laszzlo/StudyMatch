package de.htw_berlin.studymatch.backend.controller.dto;

public record UserRequest(
   String vorname,
   String email,
   String password
) {}
