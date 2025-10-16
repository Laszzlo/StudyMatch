package de.htw_berlin.studymatch.backend.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Setter(AccessLevel.NONE)
    private Long uid;
    private String vorname;
    private String nachname;
    private String email;
    private String passwort;
}
