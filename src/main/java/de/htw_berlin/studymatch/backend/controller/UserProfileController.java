package de.htw_berlin.studymatch.backend.controller;

import de.htw_berlin.studymatch.backend.controller.dto.ProfileRequest;
import de.htw_berlin.studymatch.backend.controller.dto.ProfileResponse;
import de.htw_berlin.studymatch.backend.model.OnboardingStatus;
import de.htw_berlin.studymatch.backend.model.UserPrincipal;
import de.htw_berlin.studymatch.backend.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
@AllArgsConstructor
public class UserProfileController {

    private final ProfileService profileService;

    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getMyProfile(
            @AuthenticationPrincipal UserPrincipal currentUser) {
        return ResponseEntity.ok(profileService.getProfileByUserId(currentUser.getUserId()));
    }

    @PatchMapping("/me")
    public ResponseEntity<ProfileResponse> updateProfile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestBody ProfileRequest request) {
        ProfileResponse updated = profileService.updateProfile(currentUser.getUserId(), request);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable Long id) {
        // TODO: Match-Check hinzufügen
        return ResponseEntity.ok(profileService.getProfileById(id));
    }

    @GetMapping("/discover")
    public ResponseEntity<List<ProfileResponse>> getDiscoverProfiles(
            @AuthenticationPrincipal UserPrincipal currentUser) {
        return ResponseEntity.ok(profileService.getDiscoverProfiles(currentUser.getUserId()));
    }

    @GetMapping("/onboarding/status")
    public ResponseEntity<OnboardingStatus> getOnboardingStatus(
            @AuthenticationPrincipal UserPrincipal currentUser) {
        return ResponseEntity.ok(profileService.getOnboardingStatus(currentUser.getUserId()));
    }
}