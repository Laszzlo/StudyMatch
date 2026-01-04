package de.htw_berlin.studymatch.backend.controller.dto;

public record MatchResponse(
        Long matchId,
        Long otherUserId,
        String otherUserVorname,
        String otherUserUsername,
        String profilePictureUrl
) {}
