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
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_a_id", nullable = false)
    private User userA;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_b_id", nullable = false)
    private User userB;


    @Column (name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @Enumerated(EnumType.STRING)
    @Column (name = "match_status", nullable = false)
    private MatchStatus matchStatus;

    @PrePersist
    public void onCreate(){
        this.createdAt=LocalDateTime.now();
    }
}
