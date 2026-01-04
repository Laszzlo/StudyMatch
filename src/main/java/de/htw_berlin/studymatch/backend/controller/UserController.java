package de.htw_berlin.studymatch.backend.controller;
import de.htw_berlin.studymatch.backend.controller.dto.ProfileResponse;
import de.htw_berlin.studymatch.backend.controller.dto.UserResponse;
import de.htw_berlin.studymatch.backend.model.Profile;
import de.htw_berlin.studymatch.backend.model.User;
import de.htw_berlin.studymatch.backend.model.UserPrincipal;
import de.htw_berlin.studymatch.backend.service.ProfileService;
import de.htw_berlin.studymatch.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUser(){
        List<UserResponse> user = userService.getAllUsersExceptCurrent();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(
            @AuthenticationPrincipal UserPrincipal userPrincipal){
        User user = userPrincipal.getUser();
        ProfileResponse profileResponse = profileService.getProfileByUserId(user.getId());
        return ResponseEntity.ok(
                new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getRole(),
                        profileResponse
                )
        );
    }
}
