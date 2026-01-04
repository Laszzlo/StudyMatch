package de.htw_berlin.studymatch.backend.service;
import de.htw_berlin.studymatch.backend.controller.dto.LoginResponse;
import de.htw_berlin.studymatch.backend.model.User;
import de.htw_berlin.studymatch.backend.model.UserPrincipal;
import de.htw_berlin.studymatch.backend.repository.UserRepository;
import de.htw_berlin.studymatch.backend.security.JwtService;
import de.htw_berlin.studymatch.exceptions.UserAlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public User register(String username, String password){
        Optional<User> exists = userRepository.findByUsername(username);
        if(exists.isEmpty()) {
            User newUser = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password)).build();

            return userRepository.save(newUser);

        } else {
            throw new UserAlreadyExistsException("Es existiert bereits ein User mit dieser Email.");
        }
    }

    public LoginResponse login(String username, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        if (authentication.isAuthenticated()){
            return new LoginResponse(
                    jwtService.generateToken(new UserPrincipal(User.builder().username(username).password(password).build()))
            );
        } else {
            return new LoginResponse("Failed");
        }
    }
}
