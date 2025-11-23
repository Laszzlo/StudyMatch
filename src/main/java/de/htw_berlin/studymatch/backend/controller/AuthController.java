package de.htw_berlin.studymatch.backend.controller;

import de.htw_berlin.studymatch.backend.controller.dto.*;
import de.htw_berlin.studymatch.backend.model.User;
import de.htw_berlin.studymatch.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;



@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request){
        User created = authService.register(
                request.vorname(),
                request.username(),
                request.password(),
                request.img(),
                request.role()
        );
        URI location = URI.create("/api/users/" + created.getId());
        return ResponseEntity.created(location).body(
                new RegisterResponse(
                        created.getId(),
                        created.getVorname(),
                        created.getUsername(),
                        created.getRole()
                )
        );
    }

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> loginResonseResponseEntity(@RequestBody LoginRequest request){
//        LoginResponse response = authService.login(request.username(), request.password());
//        return ResponseEntity.ok(response);
//    }
}
