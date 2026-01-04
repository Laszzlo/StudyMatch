package de.htw_berlin.studymatch.backend.model;

public record OnboardingStatus(
        int currentStep,
        boolean isComplete,
        boolean hasVorname,           // Schritt 1
        boolean hasProfilePicture,    // Schritt 2
        boolean hasGender,            // Schritt 3
        boolean hasBirthdate,         // Schritt 4
        boolean hasBio,               // Schritt 5
        boolean hasCourse,            // Schritt 6 (Studiengang)
        boolean hasSemester,          // Schritt 7
        boolean hasSubjects,          // Schritt 8 (Fächer)
        boolean hasLanguages          // Schritt 9
) {}