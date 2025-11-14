package de.htw_berlin.studymatch.backend.controller;
import de.htw_berlin.studymatch.backend.controller.dto.UserRequest;
import de.htw_berlin.studymatch.backend.controller.dto.UserResponse;
import jakarta.validation.Valid;
import de.htw_berlin.studymatch.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUser(){
        List<UserResponse> user = userService.getAllUser();
        return ResponseEntity.ok(user);
    }
    @PostMapping("auth/register")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request){
        UserResponse created = userService.createUser(request);
        URI location = URI.create("/api/user/auth/register" + created.id());
        return ResponseEntity.created(location).body(created);
    }
}
