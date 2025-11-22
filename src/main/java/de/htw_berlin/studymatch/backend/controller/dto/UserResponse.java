package de.htw_berlin.studymatch.backend.controller.dto;

import de.htw_berlin.studymatch.backend.model.Role;

public record UserResponse(
   Long id,
   String vorname,
   String username,
   String img,
   Role role
) {}
