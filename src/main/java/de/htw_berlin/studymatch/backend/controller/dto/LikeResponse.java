package de.htw_berlin.studymatch.backend.controller.dto;

public record LikeResponse(
        Long id,
        String fromUserVorname,
        String fromUserUsername,
        String toUserVorname,
        String toUserUsername,
        boolean isMatch
) {}
