package de.htw_berlin.studymatch.service;

import de.htw_berlin.studymatch.backend.controller.dto.LikeResponse;
import de.htw_berlin.studymatch.backend.model.*;
import de.htw_berlin.studymatch.backend.repository.LikeRepository;
import de.htw_berlin.studymatch.backend.repository.MatchRepository;
import de.htw_berlin.studymatch.backend.repository.ProfileRepository;
import de.htw_berlin.studymatch.backend.repository.UserRepository;
import de.htw_berlin.studymatch.backend.service.LikeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LikeServiceTest {

    @Autowired
    private LikeService likeService;

    @MockitoBean
    private LikeRepository likeRepository;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private MatchRepository matchRepository;

    @MockitoBean
    private ProfileRepository profileRepository;

    @Test
    @DisplayName("Should create a like successfully.")
    void testCreateLike() {
        User fromUser = User.builder().id(1L).username("max@test.com").build();
        User toUser = User.builder().id(2L).username("erika@test.com").build();

        Profile profile1 = Profile.builder().user(fromUser).vorname("Max").build();
        Profile profile2 = Profile.builder().user(toUser).vorname("Erika").build();

        Like like = Like.builder().id(1L).fromUser(fromUser).toUser(toUser).build();

        doReturn(Optional.of(fromUser)).when(userRepository).findById(1L);
        doReturn(Optional.of(toUser)).when(userRepository).findById(2L);
        doReturn(like).when(likeRepository).save(any(Like.class));
        doReturn(Optional.of(profile1)).when(profileRepository).findByUser_Id(1L);
        doReturn(Optional.of(profile2)).when(profileRepository).findByUser_Id(2L);
        doReturn(false).when(likeRepository).existsByFromUserIdAndToUserId(1L, 2L);

        LikeResponse likeResponse = likeService.createLike(1L, 2L);
        Assertions.assertEquals("Max", likeResponse.fromUserVorname());
        Assertions.assertEquals("Erika", likeResponse.toUserVorname());
        Assertions.assertFalse(likeResponse.isMatch());
    }
}
