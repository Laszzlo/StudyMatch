package de.htw_berlin.studymatch.backend.service;

import de.htw_berlin.studymatch.backend.controller.dto.MatchResponse;
import de.htw_berlin.studymatch.backend.model.Match;
import de.htw_berlin.studymatch.backend.model.Profile;
import de.htw_berlin.studymatch.backend.model.User;
import de.htw_berlin.studymatch.backend.repository.MatchRepository;
import de.htw_berlin.studymatch.backend.repository.ProfileRepository;
import de.htw_berlin.studymatch.exceptions.ProfileNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final ProfileRepository profileRepository;
    public List<MatchResponse> getMyMatches(Long currentUserId) {
        List<Match> matches = matchRepository.findAllByUserId(currentUserId);

        return matches.stream()
                .map(match -> toMatchResponse(match, currentUserId))
                .toList();
    }

    private MatchResponse toMatchResponse(Match match, Long currentUserId) {
        // Finde den ANDEREN User (nicht mich selbst)
        User otherUser;
        if (match.getUserA().getId().equals(currentUserId)) {
            otherUser = match.getUserB();  // Ich bin A → zeige B
        } else {
            otherUser = match.getUserA();  // Ich bin B → zeige A
        }

        Profile profile = profileRepository.findByUser_Id(otherUser.getId())
                .orElseThrow(() -> new ProfileNotFoundException("Kein Profil für User mit id: " + otherUser.getId() + " gefunden"));

        return new MatchResponse(
                match.getId(),
                otherUser.getId(),
                profile.getVorname(),
                otherUser.getUsername(),
                profile.getProfilePictureUrl()
        );
    }
}
