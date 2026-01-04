package de.htw_berlin.studymatch.backend.controller;

import de.htw_berlin.studymatch.backend.controller.dto.*;
import de.htw_berlin.studymatch.backend.model.User;
import de.htw_berlin.studymatch.backend.service.AuthService;
import de.htw_berlin.studymatch.backend.service.ProfileService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;



@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request){
        User created = authService.register(
                request.username(),
                request.password()
        );
        profileService.createProfile(created.getId());
        URI location = URI.create("/api/users/" + created.getId());
        return ResponseEntity.created(location).body(
                new RegisterResponse(
                        created.getId(),
                        created.getUsername()
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        LoginResponse response = authService.login(request.username(), request.password());
        System.out.println(response.token());
        return ResponseEntity.ok(response);
    }
}
