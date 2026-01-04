package de.htw_berlin.studymatch.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @Length(min = 6)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
