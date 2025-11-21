package de.htw_berlin.studymatch.backend.controller;

import de.htw_berlin.studymatch.backend.controller.dto.LoginRequest;
import de.htw_berlin.studymatch.backend.controller.dto.LoginResponse;
import de.htw_berlin.studymatch.backend.controller.dto.RegisterRequest;
import de.htw_berlin.studymatch.backend.controller.dto.RegisterResponse;
import de.htw_berlin.studymatch.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;



@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin("http://localhost:5173")
public class AuthController {
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> createUser(@Valid @RequestBody RegisterRequest request){
        RegisterResponse created = authService.register(request);
        URI location = URI.create("/api/user/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginResonseResponseEntity(@RequestBody LoginRequest request){
        return null;
    }
}
