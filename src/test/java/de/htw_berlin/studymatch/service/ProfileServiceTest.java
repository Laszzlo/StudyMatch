package de.htw_berlin.studymatch.service;

import de.htw_berlin.studymatch.backend.model.OnboardingStatus;
import de.htw_berlin.studymatch.backend.controller.dto.ProfileRequest;
import de.htw_berlin.studymatch.backend.controller.dto.ProfileResponse;
import de.htw_berlin.studymatch.backend.model.*;
import de.htw_berlin.studymatch.backend.repository.*;
import de.htw_berlin.studymatch.backend.service.ProfileService;
import de.htw_berlin.studymatch.exceptions.CourseNotFoundException;
import de.htw_berlin.studymatch.exceptions.ProfileAlreadyExistsException;
import de.htw_berlin.studymatch.exceptions.ProfileNotFoundException;
import de.htw_berlin.studymatch.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @MockitoBean
    private ProfileRepository profileRepository;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private CourseRepository courseRepository;

    @MockitoBean
    private SubjectRepository subjectRepository;

    @MockitoBean
    private LanguageRepository languageRepository;

    @MockitoBean
    private LikeRepository likeRepository;

    @MockitoBean
    private MatchRepository matchRepository;


    @Test
    @DisplayName("Should create profile for user.")
    void testCreateProfile() {
        User user = User.builder().id(1L).username("max@test.de").build();

        Profile savedProfile = Profile.builder()
                .id(1L)
                .user(user)
                .onboardingStep(1)
                .profileComplete(false)
                .subjects(new HashSet<>())
                .languages(new HashSet<>())
                .build();

        doReturn(Optional.of(user)).when(userRepository).findById(1L);
        doReturn(Optional.empty()).when(profileRepository).findByUser_Id(1L);
        doReturn(savedProfile).when(profileRepository).save(any(Profile.class));

        Profile result = profileService.createProfile(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getOnboardingStep());
        Assertions.assertFalse(result.getProfileComplete());
        verify(profileRepository, times(1)).save(any(Profile.class));
    }
}
