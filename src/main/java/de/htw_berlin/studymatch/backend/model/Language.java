package de.htw_berlin.studymatch.backend.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "languages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;
    @Column
    private String name;
}
