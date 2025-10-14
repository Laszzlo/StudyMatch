package de.htw_berlin.studymatch.backend.controller;
import de.htw_berlin.studymatch.backend.Greeting;
import de.htw_berlin.studymatch.backend.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class UserController {

    @GetMapping("/user")
    public List<User> getUser() {
        return List.of(User.builder()
                .vorname("Mia")
                .nachname("Mitrovic")
                .email("mia@mitrovic.com")
                .build(),
                User.builder()
                .vorname("Laszlo")
                .nachname("Imanuel")
                .email("laszlo@imanuel.com")
                .build());
    }

}
