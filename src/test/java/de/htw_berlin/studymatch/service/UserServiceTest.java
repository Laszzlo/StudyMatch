package de.htw_berlin.studymatch.service;

import de.htw_berlin.studymatch.backend.controller.dto.UserResponse;
import de.htw_berlin.studymatch.backend.model.*;
import de.htw_berlin.studymatch.backend.repository.LikeRepository;
import de.htw_berlin.studymatch.backend.repository.MatchRepository;
import de.htw_berlin.studymatch.backend.repository.ProfileRepository;
import de.htw_berlin.studymatch.backend.repository.UserRepository;
import de.htw_berlin.studymatch.backend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import java.util.List;
import static org.mockito.Mockito.doReturn;


@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private ProfileRepository profileRepository;

    @MockitoBean
    private LikeRepository likeRepository;

    @MockitoBean
    private MatchRepository matchRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .username("max@test.de")
                .role(Role.USER)
                .build();

    }

    @Test
    @DisplayName("Should exclude liked users from result.")
    void testGetAllUsersExceptCurrentExcludeLiked() {
        UserPrincipal principal = new UserPrincipal(testUser);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        User likedUser = User.builder().id(2L).username("lisa@test.de").role(Role.USER).build();

        doReturn(List.of(likedUser)).when(userRepository).findAllByIdNotOrderByRandom(1L);
        doReturn(List.of(2L)).when(likeRepository).findAllToUserIdsByFromUserId(1L);
        doReturn(List.of()).when(matchRepository).findAllMatchedUserIdsByUserId(1L);

        List<UserResponse> users = userService.getAllUsersExceptCurrent();

        Assertions.assertTrue(users.isEmpty());
    }

    @Test
    @DisplayName("Should exclude matched users from result.")
    void testGetAllUsersExceptCurrentExcludeMatched() {
        UserPrincipal principal = new UserPrincipal(testUser);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        User matchedUser = User.builder().id(2L).username("lisa@test.de").role(Role.USER).build();

        doReturn(List.of(matchedUser)).when(userRepository).findAllByIdNotOrderByRandom(1L);
        doReturn(List.of()).when(likeRepository).findAllToUserIdsByFromUserId(1L);
        doReturn(List.of(2L)).when(matchRepository).findAllMatchedUserIdsByUserId(1L);

        List<UserResponse> users = userService.getAllUsersExceptCurrent();

        Assertions.assertTrue(users.isEmpty());
    }
}
