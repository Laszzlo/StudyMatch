package de.htw_berlin.studymatch.backend.service;
import de.htw_berlin.studymatch.backend.controller.dto.UserRequest;
import de.htw_berlin.studymatch.backend.controller.dto.UserResponse;
import de.htw_berlin.studymatch.backend.model.User;
import de.htw_berlin.studymatch.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.dialect.function.DB2SubstringFunction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@AllArgsConstructor
public class UserService {

    private UserResponse toResponse(User user){
        return new UserResponse(
                user.getUid(),
                user.getVorname(),
                user.getEmail()
        );
    }
    private final UserRepository userRepository;
    public List<UserResponse> getAllUser(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    @Transactional
    public UserResponse addUser(UserRequest request){
        Optional<User> existing = userRepository.findByUid(request.uid());
        if(existing.isEmpty()){
            return null;
        }
        return null;
    }
}
