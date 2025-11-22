package de.htw_berlin.studymatch.backend.service;
import de.htw_berlin.studymatch.backend.controller.dto.UserResponse;
import de.htw_berlin.studymatch.backend.model.User;
import de.htw_berlin.studymatch.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@AllArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private UserResponse toResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getVorname(),
                user.getUsername(),
                user.getImg(),
                user.getRole()
        );
    }
    private final UserRepository userRepository;
    public List<UserResponse> getAllUser(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
