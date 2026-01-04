package de.htw_berlin.studymatch.backend.service;
import de.htw_berlin.studymatch.backend.controller.dto.LanguageResponse;
import de.htw_berlin.studymatch.backend.controller.dto.ProfileResponse;
import de.htw_berlin.studymatch.backend.controller.dto.SubjectResponse;
import de.htw_berlin.studymatch.backend.controller.dto.UserResponse;
import de.htw_berlin.studymatch.backend.model.*;
import de.htw_berlin.studymatch.backend.repository.LikeRepository;
import de.htw_berlin.studymatch.backend.repository.MatchRepository;
import de.htw_berlin.studymatch.backend.repository.ProfileRepository;
import de.htw_berlin.studymatch.backend.repository.UserRepository;
import de.htw_berlin.studymatch.exceptions.ProfileNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@AllArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final MatchRepository matchRepository;
    private final ProfileRepository profileRepository;

    private UserResponse toResponse(User user){
        Profile profile = profileRepository.findByUser_Id(user.getId())
                .orElseThrow(()-> new ProfileNotFoundException("Kein Profil für User mit id: " + user.getId() + " gefunden."));

        ProfileResponse profileResponse = new ProfileResponse(
                profile.getId(),
                profile.getVorname(),
                profile.getGender(),
                profile.getBio(),
                profile.getBirthdate(),
                profile.getSemester(),
                profile.getProfilePictureUrl(),
                mapCourseToName(profile.getCourse()),
                mapSubjectsToResponse(profile.getSubjects()),
                mapLanguagesToResponse(profile.getLanguages()),
                profile.getOnboardingStep(),
                profile.getProfileComplete(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                profileResponse
        );
    }

    public List<UserResponse> getAllUser(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<UserResponse> getAllUsersExceptCurrent() {
        Long currentUserId = getCurrentUserId();
        List<User> allUsers = userRepository.findAllByIdNotOrderByRandom(currentUserId);

        List<Long> likedUserIds = likeRepository.findAllToUserIdsByFromUserId(currentUserId);

        List<Long> matchedUserIds = matchRepository.findAllMatchedUserIdsByUserId(currentUserId);

        List<User> filteredUsers = allUsers.stream()
                .filter(user -> !likedUserIds.contains(user.getId()))
                .filter(user -> !matchedUserIds.contains(user.getId()))
                .collect(Collectors.toList());

        Collections.shuffle(filteredUsers);

        return filteredUsers.stream()
                .map(user -> {
                    Profile profile = profileRepository
                            .findByUser_Id(user.getId())
                            .orElseThrow(()-> new ProfileNotFoundException("Kein Profil für User mit id: " + user.getId() + " gefunden."));

                    ProfileResponse profileResponse = null;

                    if (profile != null) {
                        profileResponse = mapToProfileResponse(profile);
                    }

                    return new UserResponse(
                            user.getId(),
                            user.getUsername(),
                            user.getRole(),
                            profileResponse
                    );
                })
                .toList();
    }


    private Long getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        assert principal != null;
        return principal.getUser().getId();
    }
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

    private ProfileResponse mapToProfileResponse(Profile profile) {
        return new ProfileResponse(
                profile.getId(),
                profile.getVorname(),
                profile.getGender(),
                profile.getBio(),
                profile.getBirthdate(),
                profile.getSemester(),
                profile.getProfilePictureUrl(),
                profile.getCourse() != null ? profile.getCourse().getName() : null,
                profile.getSubjects().stream()
                        .map(s -> new SubjectResponse(s.getId(), s.getName()))
                        .collect(Collectors.toSet()),
                profile.getLanguages().stream()
                        .map(l -> new LanguageResponse(l.getId(), l.getName()))
                        .collect(Collectors.toSet()),
                profile.getOnboardingStep(),
                profile.getProfileComplete(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }
}
