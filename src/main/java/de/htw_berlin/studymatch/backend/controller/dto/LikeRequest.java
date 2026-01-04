package de.htw_berlin.studymatch.backend.controller.dto;

public record LikeRequest(
        Long fromUserId,
        Long toUserId
) {}
