package de.htw_berlin.studymatch.backend.controller.dto;

import de.htw_berlin.studymatch.backend.model.Gender;

import java.time.LocalDate;
import java.util.Set;

public record ProfileRequest(
        String vorname,
        Gender gender,
        String bio,
        LocalDate birthdate,
        Integer semester,
        String profilePictureUrl,
        Integer courseId,
        Set<Integer> subjectIds,
        Set<Integer> languageIds
) {}
