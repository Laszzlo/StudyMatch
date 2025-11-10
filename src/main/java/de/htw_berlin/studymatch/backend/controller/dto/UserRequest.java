package de.htw_berlin.studymatch.backend.controller.dto;

public record UserRequest(
   Long uid,
   String vorname,
   String email
) {}
