package de.htw_berlin.studymatch.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(name = "user_a_id", nullable = false)
    private Long userAid;
    @Column (name = "user_b_id", nullable = false)
    private Long userBid;
    @Column (name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    @Column (name = "match_status", nullable = false)
    private MatchStatus matchStatus;
}
