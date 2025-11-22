package de.htw_berlin.studymatch.backend.service;
import de.htw_berlin.studymatch.backend.controller.dto.LoginResponse;
import de.htw_berlin.studymatch.backend.controller.dto.RegisterResponse;
import de.htw_berlin.studymatch.backend.model.Role;
import de.htw_berlin.studymatch.backend.model.User;
import de.htw_berlin.studymatch.backend.repository.UserRepository;
import de.htw_berlin.studymatch.backend.security.JwtService;
import de.htw_berlin.studymatch.exceptions.UserAlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    //private final AuthenticationManager authenticationManager;

    private RegisterResponse registerResponse(User user){
        return new RegisterResponse(
                user.getId(),
                user.getVorname(),
                user.getUsername(),
                user.getRole()
        );
    }
    @Transactional
    public User register(String vorname, String username, String password, String img, Role role){
        Optional<User> exists = userRepository.findByUsername(username);
        if(exists.isEmpty()) {
            User newUser = User.builder()
                    .vorname(vorname)
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .img(img)
                    .role(role).build();

            return userRepository.save(newUser);

        } else {
            throw new UserAlreadyExistsException("Es existiert bereits ein User mit dieser Email.");
        }
    }

//    public LoginResponse login(String username, String password){
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password)
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(()-> new UsernameNotFoundException("User not Found"));
//
//        String jwtToken = jwtService.generateToken(user);
//        return new LoginResponse(jwtToken);
//    }
}
