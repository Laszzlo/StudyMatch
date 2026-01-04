package de.htw_berlin.studymatch.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "profiles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String vorname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private String bio;

    @Column
    private LocalDate birthdate;

    @Column
    private Integer semester;

    @Column
    private String profilePictureUrl;

    @ManyToMany
    @Builder.Default
    @JoinTable(name = "profile_subjects", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Set<Subject> subjects = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "course_id")
    private Course course;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profile_languages")
    private Set<Language> languages;

    @Column(name = "onboarding_step")
    private Integer onboardingStep;

    @Column(name = "profile_complete")
    private Boolean profileComplete;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
