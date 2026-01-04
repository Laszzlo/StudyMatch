package de.htw_berlin.studymatch.backend.controller;

import de.htw_berlin.studymatch.backend.controller.dto.LikeResponse;
import de.htw_berlin.studymatch.backend.controller.dto.MatchResponse;
import de.htw_berlin.studymatch.backend.model.UserPrincipal;
import de.htw_berlin.studymatch.backend.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/matches")
@AllArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @GetMapping()
    public ResponseEntity<List<MatchResponse>> getMyMatches(@AuthenticationPrincipal UserPrincipal currentUser) {
        return ResponseEntity.ok(matchService.getMyMatches(currentUser.getUserId()));
    }
}
