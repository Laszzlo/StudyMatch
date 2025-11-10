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

public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User fromUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User toUserId;

    @Column (name = "created_at")
    private LocalDateTime createdAt;
}
