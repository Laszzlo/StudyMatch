package de.htw_berlin.studymatch.backend.service;

import de.htw_berlin.studymatch.backend.controller.dto.*;
import de.htw_berlin.studymatch.backend.model.*;
import de.htw_berlin.studymatch.backend.repository.*;
import de.htw_berlin.studymatch.exceptions.CourseNotFoundException;
import de.htw_berlin.studymatch.exceptions.ProfileAlreadyExistsException;
import de.htw_berlin.studymatch.exceptions.ProfileNotFoundException;
import de.htw_berlin.studymatch.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;
    private final LanguageRepository languageRepository;
    private final LikeRepository likeRepository;
    private final MatchRepository matchRepository;

    @Transactional
    public Profile createProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User mit id: " + userId + " nicht gefunden."));

        if (profileRepository.findByUser_Id(userId).isPresent()) {
            throw new ProfileAlreadyExistsException("Es gibt bereits ein Profil mit user_id: " + userId);
        }

        Profile profile = Profile.builder()
                .user(user)
                .onboardingStep(1)
                .profileComplete(false)
                .subjects(new HashSet<>())   // Fächer (leer initialisieren)
                .languages(new HashSet<>())  // Sprachen (leer initialisieren)
                .build();

        return profileRepository.save(profile);
    }

    @Transactional
    public ProfileResponse updateProfile(Long userId, ProfileRequest request) {
        Profile profile = profileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Kein Profil für User mit id: " + userId + " gefunden"));

        if (request.vorname() != null) {
            profile.setVorname(request.vorname());
        }
        if (request.gender() != null) {
            profile.setGender(request.gender());
        }
        if (request.bio() != null) {
            profile.setBio(request.bio());
        }
        if (request.birthdate() != null) {
            profile.setBirthdate(request.birthdate());
        }
        if (request.semester() != null) {
            profile.setSemester(request.semester());
        }
        if (request.profilePictureUrl() != null) {
            profile.setProfilePictureUrl(request.profilePictureUrl());
        }

        // Studiengang (Course - einer)
        if (request.courseId() != null) {
            Course course = courseRepository.findById(request.courseId())
                    .orElseThrow(() -> new CourseNotFoundException("Studiengang mit id: " + request.courseId() + " nicht gefunden"));
            profile.setCourse(course);
        }

        // Fächer (Subjects - mehrere)
        if (request.subjectIds() != null) {
            Set<Subject> subjects = new HashSet<>(subjectRepository.findAllById(request.subjectIds()));
            profile.setSubjects(subjects);
        }

        // Sprachen (mehrere)
        if (request.languageIds() != null) {
            Set<Language> languages = new HashSet<>(languageRepository.findAllById(request.languageIds()));
            profile.setLanguages(languages);
        }

        updateOnboardingStatus(profile);
        Profile saved = profileRepository.save(profile);
        return profileResponse(saved);
    }

    public ProfileResponse getProfileByUserId(Long userId) {
        Profile profile = profileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Kein Profil für User mit id: " + userId + " gefunden"));

        return profileResponse(profile);
    }

    public List<ProfileResponse> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        return profiles.stream().map(this::profileResponse).toList();
    }

    public ProfileResponse getProfileById(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Kein Profil mit id: " + id + " gefunden"));
        return profileResponse(profile);
    }

    public OnboardingStatus getOnboardingStatus(Long userId) {
        Profile profile = profileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Kein Profil für User mit id: " + userId + " gefunden"));
        return calculateOnboardingStatus(profile);
    }

    public List<ProfileResponse> getDiscoverProfiles(Long userId) {
        // 1. Alle User-IDs die ich bereits geliked habe
        List<Long> likedUserIds = likeRepository.findAllToUserIdsByFromUserId(userId);

        // 2. Alle User-IDs mit denen ich bereits gematcht bin
        List<Long> matchedUserIds = matchRepository.findAllMatchedUserIdsByUserId(userId);

        // 3. Alle vollständigen Profile laden
        List<Profile> allProfiles = profileRepository.findAllByProfileCompleteTrue();

        // 4. Filtern: Nicht ich selbst, nicht geliked, nicht gematcht
        List<Profile> filteredProfiles = allProfiles.stream()
                .filter(p -> !p.getUser().getId().equals(userId))           // Nicht mich selbst
                .filter(p -> !likedUserIds.contains(p.getUser().getId()))   // Nicht bereits geliked
                .filter(p -> !matchedUserIds.contains(p.getUser().getId())) // Nicht bereits gematcht
                .collect(Collectors.toList());

        // 5. Zufällig mischen
        Collections.shuffle(filteredProfiles);

        // 6. Zu Response mappen
        return filteredProfiles.stream()
                .map(this::profileResponse)
                .toList();
    }

    // ==================== Mapping Methoden ====================

    private String mapCourseToName(Course course) {
        return course != null ? course.getName() : null;
    }

    private Set<SubjectResponse> mapSubjectsToResponse(Set<Subject> subjects) {
        if (subjects == null) return Set.of();
        return subjects.stream()
                .map(s -> new SubjectResponse(s.getId(), s.getName()))
                .collect(Collectors.toSet());
    }

    private Set<LanguageResponse> mapLanguagesToResponse(Set<Language> languages) {
        if (languages == null) return Set.of();
        return languages.stream()
                .map(l -> new LanguageResponse(l.getId(), l.getName()))
                .collect(Collectors.toSet());
    }

    private ProfileResponse profileResponse(Profile profile) {
        return new ProfileResponse(
                profile.getId(),
                profile.getVorname(),
                profile.getGender(),
                profile.getBio(),
                profile.getBirthdate(),
                profile.getSemester(),
                profile.getProfilePictureUrl(),
                mapCourseToName(profile.getCourse()),           // Studiengang (Name)
                mapSubjectsToResponse(profile.getSubjects()),   // Fächer
                mapLanguagesToResponse(profile.getLanguages()), // Sprachen
                profile.getOnboardingStep(),
                profile.getProfileComplete(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }

    // ==================== Onboarding Status ====================

    private void updateOnboardingStatus(Profile profile) {
        OnboardingStatus status = calculateOnboardingStatus(profile);
        profile.setOnboardingStep(status.currentStep());
        profile.setProfileComplete(status.isComplete());
    }

    private OnboardingStatus calculateOnboardingStatus(Profile profile) {
        // Schritt 1: Vorname
        boolean step1 = profile.getVorname() != null && !profile.getVorname().isBlank();

        // Schritt 2: Profilbild
        boolean step2 = profile.getProfilePictureUrl() != null && !profile.getProfilePictureUrl().isBlank();

        // Schritt 3: Geschlecht
        boolean step3 = profile.getGender() != null;

        // Schritt 4: Geburtsdatum
        boolean step4 = profile.getBirthdate() != null;

        // Schritt 5: Bio
        boolean step5 = profile.getBio() != null && !profile.getBio().isBlank();

        // Schritt 6: Studiengang (Course)
        boolean step6 = profile.getCourse() != null;

        // Schritt 7: Semester
        boolean step7 = profile.getSemester() != null && profile.getSemester() > 0;

        // Schritt 8: Fächer (Subjects)
        boolean step8 = profile.getSubjects() != null && !profile.getSubjects().isEmpty();

        // Schritt 9: Sprachen
        boolean step9 = profile.getLanguages() != null && !profile.getLanguages().isEmpty();

        // Aktuellen Schritt berechnen (erster nicht ausgefüllter Schritt)
        int currentStep = 1;
        if (step1) currentStep = 2;
        if (step1 && step2) currentStep = 3;
        if (step1 && step2 && step3) currentStep = 4;
        if (step1 && step2 && step3 && step4) currentStep = 5;
        if (step1 && step2 && step3 && step4 && step5) currentStep = 6;
        if (step1 && step2 && step3 && step4 && step5 && step6) currentStep = 7;
        if (step1 && step2 && step3 && step4 && step5 && step6 && step7) currentStep = 8;
        if (step1 && step2 && step3 && step4 && step5 && step6 && step7 && step8) currentStep = 9;

        boolean isComplete = step1 && step2 && step3 && step4 && step5 && step6 && step7 && step8 && step9;

        return new OnboardingStatus(
                currentStep,
                isComplete,
                step1,  // hasVorname
                step2,  // hasProfilePicture
                step3,  // hasGender
                step4,  // hasBirthdate
                step5,  // hasBio
                step6,  // hasCourse (Studiengang)
                step7,  // hasSemester
                step8,  // hasSubjects (Fächer)
                step9   // hasLanguages
        );
    }
}