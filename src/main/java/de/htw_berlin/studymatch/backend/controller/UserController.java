package de.htw_berlin.studymatch.backend.controller;
import de.htw_berlin.studymatch.backend.controller.dto.UserRequest;
import de.htw_berlin.studymatch.backend.controller.dto.UserResponse;
import de.htw_berlin.studymatch.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUser(){
        List<UserResponse> user = userService.getAllUser();
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("{username}/delete")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable("username") String username){
        UserResponse user = userService.deleteUser(username);
        return ResponseEntity.ok(user);
    }
}
