package de.htw_berlin.studymatch.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subjects")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;
    @Column
    private String name;
}
