package de.htw_berlin.studymatch.service;

import de.htw_berlin.studymatch.backend.controller.dto.MatchResponse;
import de.htw_berlin.studymatch.backend.model.Match;
import de.htw_berlin.studymatch.backend.model.MatchStatus;
import de.htw_berlin.studymatch.backend.model.Profile;
import de.htw_berlin.studymatch.backend.model.User;
import de.htw_berlin.studymatch.backend.repository.MatchRepository;
import de.htw_berlin.studymatch.backend.repository.ProfileRepository;
import de.htw_berlin.studymatch.backend.service.MatchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class MatchServiceTest {

    @Autowired
    private MatchService matchService;

    @MockitoBean
    private MatchRepository matchRepository;

    @MockitoBean
    private ProfileRepository profileRepository;

    @Test
    @DisplayName("Should return all matches for current user.")
    void testGetMyMatches() {
        User currentUser = User.builder().id(1L).username("max@test.de").build();
        User otherUser = User.builder().id(2L).username("lisa@test.de").build();

        Match match = Match.builder()
                .id(1L)
                .userA(currentUser)
                .userB(otherUser)
                .matchStatus(MatchStatus.ACTIVE)
                .build();

        Profile otherProfile = Profile.builder()
                .id(1L)
                .user(otherUser)
                .vorname("Lisa")
                .profilePictureUrl("https://example.com/lisa.jpg")
                .build();

        doReturn(List.of(match)).when(matchRepository).findAllByUserId(1L);
        doReturn(Optional.of(otherProfile)).when(profileRepository).findByUser_Id(2L);

        List<MatchResponse> matches = matchService.getMyMatches(1L);

        Assertions.assertEquals(1, matches.size());
        Assertions.assertEquals("Lisa", matches.getFirst().otherUserVorname());
        Assertions.assertEquals("lisa@test.de", matches.getFirst().otherUserUsername());
    }

}
