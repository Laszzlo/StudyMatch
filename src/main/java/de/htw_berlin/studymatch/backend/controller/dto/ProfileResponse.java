package de.htw_berlin.studymatch.backend.controller.dto;

import de.htw_berlin.studymatch.backend.model.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public record ProfileResponse(
        Long id,
        String vorname,
        Gender gender,
        String bio,
        LocalDate birthdate,
        Integer semester,
        String profilePictureUrl,
        String course,
        Set<SubjectResponse> subjects,
        Set<LanguageResponse> languages,
        Integer onboardingStep,
        Boolean profileComplete,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
