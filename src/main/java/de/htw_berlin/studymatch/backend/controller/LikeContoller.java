package de.htw_berlin.studymatch.backend.controller;

import de.htw_berlin.studymatch.backend.controller.dto.LikeResponse;
import de.htw_berlin.studymatch.backend.model.UserPrincipal;
import de.htw_berlin.studymatch.backend.service.LikeService;
import de.htw_berlin.studymatch.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/likes")
@AllArgsConstructor
public class LikeContoller {
    private final LikeService likeService;

    @PostMapping("/{toUserId}")
    public ResponseEntity<LikeResponse> createLike(@PathVariable Long toUserId, @AuthenticationPrincipal UserPrincipal currentUser){
        LikeResponse created = likeService.createLike(currentUser.getUserId(), toUserId);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/received")
    public ResponseEntity<List<LikeResponse>> getLikesReceived(@AuthenticationPrincipal UserPrincipal currentUser) {
        return ResponseEntity.ok(likeService.getLikesReceived(currentUser.getUserId()));
    }

    @GetMapping("/sent")
    public ResponseEntity<List<LikeResponse>> getLikesSent(@AuthenticationPrincipal UserPrincipal currentUser) {
        return ResponseEntity.ok(likeService.getLikesSent(currentUser.getUserId()));
    }
}
