package de.htw_berlin.studymatch.backend.controller.dto;

import de.htw_berlin.studymatch.backend.model.Gender;
import de.htw_berlin.studymatch.backend.model.Profile;
import de.htw_berlin.studymatch.backend.model.Role;

public record UserRequest(
   String username,
   String password,
   Role role,
   Profile profile
) {}
