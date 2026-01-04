package de.htw_berlin.studymatch.backend.service;
import de.htw_berlin.studymatch.backend.controller.dto.LikeResponse;
import de.htw_berlin.studymatch.backend.model.*;
import de.htw_berlin.studymatch.backend.repository.LikeRepository;
import de.htw_berlin.studymatch.backend.repository.MatchRepository;
import de.htw_berlin.studymatch.backend.repository.ProfileRepository;
import de.htw_berlin.studymatch.backend.repository.UserRepository;
import de.htw_berlin.studymatch.exceptions.ProfileNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    private final ProfileRepository profileRepository;

    private LikeResponse likeResponse(Like like, boolean isMatch){
        Profile fromProfile = profileRepository.findByUser_Id(like.getFromUser().getId())
                .orElseThrow(() -> new ProfileNotFoundException("Profil von User mit id: " + like.getFromUser().getId() + " wurde nicht gefunden."));

        Profile toProfile = profileRepository.findByUser_Id(like.getToUser().getId())
                .orElseThrow(() -> new ProfileNotFoundException("Profil von User mit id: " + like.getToUser().getId() + " wurde nicht gefunden."));
        return new LikeResponse(
                like.getId(),
                fromProfile.getVorname(),
                like.getFromUser().getUsername(),
                toProfile.getVorname(),
                like.getToUser().getUsername(),
                isMatch
        );
    }

    @Transactional
    public LikeResponse createLike(Long fromUserId, Long toUserId) {
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new EntityNotFoundException("From-User nicht gefunden"));

        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new EntityNotFoundException("To-User nicht gefunden"));

        boolean alreadyLiked = likeRepository.existsByFromUserIdAndToUserId(fromUserId, toUserId);
        if (alreadyLiked) {
            throw new IllegalArgumentException("Du hast diese Person bereits geliked!");
        }

        if (fromUserId.equals(toUserId)) {
            throw new IllegalArgumentException("Du kannst dich selbst nicht liken!");
        }

        Like like = Like.builder()
                .toUser(toUser)
                .fromUser(fromUser).build();
        Like created = likeRepository.save(like);
        boolean isMatch = likeRepository.existsByFromUserIdAndToUserId(toUserId, fromUserId);
        if (isMatch){
            Match match = Match.builder()
                    .userA(fromUser)
                    .userB(toUser)
                    .matchStatus(MatchStatus.ACTIVE).build();
            matchRepository.save(match);
            likeRepository.deleteLikeByFromUser_IdAndToUser_Id(match.getUserA().getId(), match.getUserB().getId());
            likeRepository.deleteLikeByFromUser_IdAndToUser_Id(match.getUserB().getId(), match.getUserA().getId());
        }
        return likeResponse(created, isMatch);
    }

    public List<LikeResponse> getLikesSent(Long userId) {
        // Likes VON mir → ich bin fromUser
        List<Like> likes = likeRepository.findAllByFromUserId(userId);
        return likes.stream()
                .map(like -> likeResponse(like, false))
                .toList();
    }

    public List<LikeResponse> getLikesReceived(Long userId) {
        // Likes AN mich → ich bin toUser
        List<Like> likes = likeRepository.findAllByToUserId(userId);
        return likes.stream()
                .map(like -> likeResponse(like, false))
                .toList();
    }
}