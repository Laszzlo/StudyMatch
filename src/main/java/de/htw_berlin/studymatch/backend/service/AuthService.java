package de.htw_berlin.studymatch.backend.service;
import de.htw_berlin.studymatch.backend.controller.dto.RegisterRequest;
import de.htw_berlin.studymatch.backend.controller.dto.RegisterResponse;
import de.htw_berlin.studymatch.backend.model.User;
import de.htw_berlin.studymatch.backend.repository.UserRepository;
import de.htw_berlin.studymatch.exceptions.UserAlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private RegisterResponse registerResponse(User user){
        return new RegisterResponse(
                user.getId(),
                user.getVorname(),
                user.getEmail()
        );
    }
    @Transactional
    public RegisterResponse register(RegisterRequest request){
        Optional<User> exists = userRepository.findByEmail(request.email());
        if(exists.isEmpty()) {
            User newUser = User.builder()
                    .vorname(request.vorname())
                    .email(request.email())
                    .passwort(passwordEncoder.encode(request.password()))
                    .img(request.img()).build();

            User saved = userRepository.save(newUser);
            return registerResponse(saved);
        } else {
            throw new UserAlreadyExistsException("Es existiert bereits ein User mit dieser Email.");
        }
    }
}
