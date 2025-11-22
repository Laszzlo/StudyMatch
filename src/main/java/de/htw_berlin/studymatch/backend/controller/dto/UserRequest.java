package de.htw_berlin.studymatch.backend.controller.dto;

import de.htw_berlin.studymatch.backend.model.Role;

public record UserRequest(
   String vorname,
   String username,
   String password,
   String img,
   Role role
) {}
